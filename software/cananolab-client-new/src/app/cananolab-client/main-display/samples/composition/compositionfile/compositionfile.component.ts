import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { Router } from '@angular/router';
import { NavigationService } from '../../../../common/services/navigation.service';
import { ApiService } from '../../../../common/services/api.service';
@Component({
  selector: 'canano-compositionfile',
  templateUrl: './compositionfile.component.html',
  styleUrls: ['../../../../../btn-bravo-canano.scss','./compositionfile.component.scss']
})
export class CompositionfileComponent implements OnInit {
    dataId;
    setupData;
    data;
    dataTrailer;
    errors;
    message;
    theFile;
    sampleId = Properties.CURRENT_SAMPLE_ID;
    sampleName = Properties.CURRENT_SAMPLE_NAME;
    helpUrl = Consts.HELP_URL_SAMPLE_CHARACTERIZATION;
    toolHeadingNameManage = 'Sample ' + this.sampleName + ' Composition File';
    serverUrl = Properties.API_SERVER_URL;

  constructor( private apiService:ApiService,private navigationService:NavigationService,private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
  }

    ngOnInit(): void{
        this.navigationService.setCurrentSelectedItem(1);
        this.route.params.subscribe(
            ( params: Params ) => {
               this.dataId=params['dataId'];
               this.sampleId=params['sampleId'];
               if(
                this.sampleId <= 0 ){
                this.sampleId = Properties.CURRENT_SAMPLE_ID;
                }else{
                    Properties.CURRENT_SAMPLE_ID = this.sampleId;
                };
                this.apiService.getSampleName(this.sampleId).subscribe(
                    data=>this.toolHeadingNameManage='Edit ' +data['sampleName'] + ' Composition File'
                )
                let url = this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/compositionFile/setup?sampleId='+this.sampleId);
                url.subscribe(data=> {
                    this.errors={};
                    Properties.SAMPLE_TOOLS=true;
                    this.setupData=data;
                },
                error=> {
                    this.errors=error;
                });
                if (this.dataId) {
                    let url = this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/compositionFile/edit?sampleId='+this.sampleId+'&dataId='+this.dataId);
                    url.subscribe(data=> {
                        this.errors={};
                        this.data=data;
                        this.dataTrailer=JSON.parse(JSON.stringify(this.data));
                    },
                    error=> {
                        this.errors=error;

                    })
                }
                else {
                    this.setupNewFile();
                    this.dataTrailer=JSON.parse(JSON.stringify(this.data))
                }
               }

        );
    }

    delete() {
        if(confirm("Are you sure you wish to delete this composition file?"))
        {
            let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/compositionFile/delete',this.data);
            url.subscribe(data=> {
                this.errors={};
                this.router.navigate( ['home/samples/composition', this.sampleId] );
            },
            error=> {
                this.errors=error;

            })
        }
    }

    isFileUploadValid() {
        if (this.data.type!=''&&this.data.title!='') {
          if (this.data.uriExternal) {
              if (this.data.externalUrl!='') {
                  return false;
              }
          }
          if (!this.data.uriExternal) {
              if (this.theFile) {
                  return false
              }
          }
        }
        return true;
    };

    saveFile() {
        if (this.data.uriExternal) {
          let saveUrl=this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/compositionFile/submit',this.data) ;
          saveUrl.subscribe(data=> {
              this.data=data;
              this.errors={};
              this.router.navigate( ['home/samples/composition', this.sampleId] );
          },
          error=> {
            this.errors=error;
        })
        }
        else {
          this.theFile.append('uriExternal',this.data['uriExternal']);
          this.theFile.append('externalUrl',this.data['externalUrl']);
          this.theFile.append('type',this.data['type']);
          this.theFile.append('title',this.data['title']);
          this.theFile.append('keywordsStr',this.data['keywordsStr']);
          this.theFile.append('description',this.data['description']);
          let uploadUrl = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/core/uploadFile', this.theFile);
          uploadUrl.subscribe(data=> {
                this.errors={};
                this.data['uri']=data['fileName'];
                let saveUrl=this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/compositionFile/submit',this.data) ;
                saveUrl.subscribe(data=> {
                this.errors={};
                  this.data=data;
                  this.router.navigate( ['home/samples/composition', this.sampleId] );

              },
              error=> {
                this.errors=error;
            })
          },
          error=> {
            this.errors=error;

          })
        }
        console.log('i am saving')
    };

    setupNewFile() {
        this.data={
            "uriExternal": false,
            "type": "",
            "title": "",
            "description": "",
            "keywordsStr": "",
            "sampleId": this.sampleId,
            "externalUrl": null
        }
    }

    uploadFile(event) {
        this.theFile = new FormData();
        const tFile = event.target.files.item(0);
        this.theFile.append('myFile', tFile, tFile.name);
      }

    reset() {
        this.data=JSON.parse(JSON.stringify(this.dataTrailer));
    }



}

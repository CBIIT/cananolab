import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Properties } from '../../../../../assets/properties';
import { Consts } from '../../../../constants';
import { timeout } from 'rxjs/operators';
import { NavigationService } from '../../../common/services/navigation.service';
import { ApiService } from '../../../common/services/api.service';
import { StatusDisplayService } from 'src/app/cananolab-client/status-display/status-display.service';
@Component( {
    selector: 'canano-characterization',
    templateUrl: './characterization.component.html',
    styleUrls: ['./characterization.component.scss']
} )
export class CharacterizationComponent implements OnInit{
    sampleId = Properties.CURRENT_SAMPLE_ID;
    sampleName;
    tempData;
    toolHeadingNameManage;
    helpUrl = Consts.HELP_URL_SAMPLE_CHARACTERIZATION;
    sectionToShow='all';
    characterizationData =
        {
            "physico-chemical characterization":[],
            "in vitro characterization":[],
            "in vivo characterization":[],
            "other":[]
        }
    types = ['physico-chemical characterization', 'in vitro characterization','in vivo characterization','other']
    editUrl=false;

    constructor( private statusDisplayService:StatusDisplayService,private apiService:ApiService,private navigationService:NavigationService, private router: Router, private route: ActivatedRoute ){
    }

    ngOnInit(): void{
        this.editUrl=this.statusDisplayService.isEditUrl();
        this.navigationService.setCurrentSelectedItem(2);
        this.route.params.subscribe(
            ( params: Params ) => {
                setTimeout(()=> {
                    Properties.SAMPLE_TOOLS = true;

                },200)
                this.sampleId = params['sampleId'];
                this.apiService.getSampleName(this.sampleId).subscribe(
                    data=>this.toolHeadingNameManage='Sample ' +data['sampleName'] + ' Characterization'
                )
                if(
                    this.sampleId <= 0 ){
                    this.sampleId = Properties.CURRENT_SAMPLE_ID;
                }else{
                    Properties.CURRENT_SAMPLE_ID = this.sampleId;
                };
                this.getCharacterizationData().subscribe( data => {
                    this.tempData=data;
                    this.separateDataSets(data);
                },
                err => {
                    console.error( 'Error ', err );
                });
            }
        );
    }

    // returns all data for this page //
    getCharacterizationData() {
        let results;
        try{
            results = this.apiService.doGet(Consts.QUERY_CHARACTERIZATION_VIEW,'sampleId='+this.sampleId).pipe( timeout( Properties.HTTP_TIMEOUT ) );
        }catch( e ){
            console.error( 'doGet Exception: ' + e );
        }

        return results;
    }


    getExperiments(data) {
        let value=data.value;
        let rows=[];
        let rowLength=0;
        let headers=[];
        value.forEach((data,index)=> {
            let key=Object.keys(data)[0];
          headers.push(key);
          rowLength=data[key].length;
        })


        for (var x=0;x<rowLength;x++) {
            let currentRow=[];
            value.forEach((data,index)=> {
              let key=Object.keys(data)[0];
              currentRow.push(data[key][x]);
          })
          rows.push(currentRow);
        }
        return [rows,headers]
    }

    // separates out all data into subsets of physico, in vivo, in vitro and other characterization types //
    separateDataSets(data) {
        let types =['in vitro characterization','in vivo characterization','physico-chemical characterization']
        data.forEach(item=> {
            item.charName=Object.keys(item.charsByAssayType)[0];
            if (item.type=='in vitro characterization') {
                this.characterizationData['in vitro characterization'].push(item);
            }
            else if (item.type=='physico-chemical characterization') {
                this.characterizationData['physico-chemical characterization'].push(item);

            }
            else if (item.type=='in vivo characterization') {
                this.characterizationData['in vivo characterization'].push(item);
            }
            else if (types.indexOf(item.type)==-1) {
                this.characterizationData['other'].push(item);

            }
        })
    }

    // splits keywords for experiments and configurations //
    splitKeywords(keywords) {
        if (keywords) {
            return keywords.split('\n')
        }
        else {
            return ''
        }
    }

    // brings up new characterization form //
    addCharacterization(type) {
        this.router.navigate( ['home/samples/characterization/edit-characterization', Properties.CURRENT_SAMPLE_ID,type] );  // @FIXME  Don't hard code these
    }

    // brings up existing characterization form //
    editCharacterization(charId,type, charClassName) {
        this.router.navigate( ['home/samples/characterization/edit-characterization', Properties.CURRENT_SAMPLE_ID,charId, charClassName, type] );  // @FIXME  Don't hard code these
    }


    showSection(value) {
        this.sectionToShow=value;
    }
}

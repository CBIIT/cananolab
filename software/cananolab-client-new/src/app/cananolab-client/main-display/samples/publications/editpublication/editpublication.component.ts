import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router,ActivatedRoute } from '@angular/router';
import { Params } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { NavigationService } from '../../../../common/services/navigation.service';
@Component({
  selector: 'canano-editpublication',
  templateUrl: './editpublication.component.html',
  styleUrls: ['../../../../../btn-bravo-canano.scss','./editpublication.component.scss']
})
export class EditpublicationComponent implements OnInit {
    sampleId = Properties.CURRENT_SAMPLE_ID;
    sampleName = Properties.CURRENT_SAMPLE_NAME;
    helpUrl = Consts.HELP_URL_SAMPLE_CHARACTERIZATION;
    toolHeadingNameManage = 'Sample ' + this.sampleName + ' Publication';
    serverUrl = Properties.API_SERVER_URL;
    type;
    setupData;

  constructor(private navigationService:NavigationService,private httpClient:HttpClient,private route:ActivatedRoute,private router:Router) { }

  ngOnInit(): void {
    this.navigationService.setCurrentSelectedItem(3);

    this.route.params.subscribe(
        ( params: Params ) => {
            this.sampleId=params['sampleId'];
            if(
                this.sampleId <= 0 ){
                this.sampleId = Properties.CURRENT_SAMPLE_ID;
            }else{
                Properties.CURRENT_SAMPLE_ID = this.sampleId;
            };
            this.type=params['type'];
            let url=this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/publication/setup');
            url.subscribe(data=> {
                Properties.SAMPLE_TOOLS = true;
            },
            error=> {

            })
        }
    );
  }

}

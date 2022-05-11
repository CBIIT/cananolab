import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';

@Component( {
    selector: 'canano-characterization',
    templateUrl: './characterization.component.html',
    styleUrls: ['./characterization.component.scss']
} )
export class CharacterizationComponent implements OnInit{
    sampleId = Properties.CURRENT_SAMPLE_ID;
    sampleName = Properties.CURRENT_SAMPLE_NAME;
    helpUrl = Consts.HELP_URL_SAMPLE_CHARACTERIZATION;
    toolHeadingNameManage = 'Sample ' + this.sampleName + ' Characterization';
    characterizationData =
        {
            "physico-chemical characterization":[],
            "in vitro characterization":[],
            "in vivo characterization":[],
            "other":[]
        }
    types = ['physico-chemical characterization', 'in vitro characterization','in vivo characterization','other']
    serverUrl = Properties.API_SERVER_URL;

    constructor( private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
    }

    ngOnInit(): void{
        this.route.params.subscribe(
            ( params: Params ) => {
                this.sampleId = params['sampleId'].replace( /^.*\?sampleId=/, '' );
                if(
                    this.sampleId <= 0 ){
                    this.sampleId = Properties.CURRENT_SAMPLE_ID;
                }else{
                    Properties.CURRENT_SAMPLE_ID = this.sampleId;
                };
                this.getCharacterizationData().subscribe( data => {
                    this.separateDataSets(data);
                },
                err => {
                    console.error( 'Error ', err );
                });
            }
        );
    }

    getCharacterizationData() {
        let results;
        try{
            results = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/sample/characterizationView?sampleId='+this.sampleId).pipe( timeout( Properties.HTTP_TIMEOUT ) );
        }catch( e ){
            console.error( 'doGet Exception: ' + e );
        }
        return results;
    }

    separateDataSets(data) {
        let types =['in vitro characterization','in vivo characterization','physico-chemical characterization']
        data.forEach(item=> {
            console.log(item)
            item.charName=Object.keys(item.charsByAssayType)[0]
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

    splitKeywords(keywords) {
        console.log(keywords)
        return keywords.split('\n')
    }
}

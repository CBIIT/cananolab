import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { url } from 'inspector';

@Component({
  selector: 'canano-editcharacterization',
  templateUrl: './editcharacterization.component.html',
  styleUrls: ['../../../../../btn-bravo-canano.scss','./editcharacterization.component.scss']
})
export class EditcharacterizationComponent implements OnInit {
    sampleId = Properties.CURRENT_SAMPLE_ID;
    sampleName = Properties.CURRENT_SAMPLE_NAME;
    helpUrl = Consts.HELP_URL_SAMPLE_CHARACTERIZATION;
    toolHeadingNameManage = 'Sample ' + this.sampleName + ' Characterization';
    data;
    dataTrailer;
    charId;
    type;
    setupData;
    charClassName;
    numDataModifier;
    constructor( private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
    }

  ngOnInit(): void {
        this.route.params.subscribe(
            ( params: Params ) => {
                console.log(params)
                this.sampleId = params['sampleId'];
                this.charId = params['charId'];
                this.type = params['type'];
                this.charClassName = params['charClassName'];
                if(
                    this.sampleId <= 0 ){
                    this.sampleId = Properties.CURRENT_SAMPLE_ID;
                }else{
                    Properties.CURRENT_SAMPLE_ID = this.sampleId;
                };
                let modifierUrl = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/getDatumNumberModifier?columnName=Number%20Modifier');
                modifierUrl.subscribe(data=> {
                    this.numDataModifier = data;
                },
                error=> {
                    console.log('error')
                })
                if (!this.charId) {
                    let url = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/setupAdd?sampleId='+this.sampleId+'&charType='+this.type);
                    url.subscribe(
                        data=>{
                            this.setupData = data;
                            this.setupDefaultDataSet();

                        },
                        error=> {
                            console.log('error')
                        }
                    );
                }
                else {
                    this.getCharacterizationData();
                }

            }
        );
    }

    setupDefaultDataSet() {
        this.data = {
            "type":this.type,
            "name":"",
            "parentSampleId":this.sampleId,
            "charId":0,
            "assayType":"",
            "protocolId":"",
            "characterizationSourceId":0,
            "characterizationDate":null,
            "charNamesForCurrentType":[],
            "property":null,
            "designMethodsDescription":null,
            "techniqueInstruments":{
                "experiments":[]
            },
            "finding":[],
            "analysisConclusion":null,
            "selectedOtherSampleNames":[],
            "copyToOtherSamples":false,
            "submitNewChar":false,
            "charTypesLookup":[],
            "protocolLookup":[],
            "charSourceLookup":[],
            "otherSampleNameLookup":[],
            "datumConditionValueTypeLookup":[],
            "assayTypesByCharNameLookup":[],
            "errors":[],
            "messages":[],
            "dirtyFindingBean":null,
            "dirtyExperimentBean":null
        };
        this.dataTrailer = JSON.parse(JSON.stringify(this.data));
    }

    changeType(type) {
        this.data.name='';
        this.setupData['assayTypesByCharNameLookup'] = [];

        let url = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/getCharNamesByCharType?charType='+type);
        url.subscribe(
            data=> {
                this.setupData.charNamesForCurrentType = data;
            },
            error=> {
                console.log('error')
            }
        )
    }

    changeName(name) {
        this.data.assayType = '';
        let assayUrl = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/getAssayTypesByCharName?charName='+name);
        assayUrl.subscribe(
            data=> {
                this.setupData['assayTypesByCharNameLookup'] = data;
            },
            error=> {
                console.log('error')
            }
        );
        let charPropertiesUrl = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/getCharProperties?charName='+name);
        charPropertiesUrl.subscribe(
            data=> {
                this.data['property'] = data;
            },
            error=> {
                console.log('error')
            }
        );

    }

    formatDate(date) {
        console.log(date)
        let tempDate = new Date(date);
        let month = (tempDate.getMonth()+1).toString();
        if (parseInt(month)<10) {
            month = '0'+month;
        }
        let day = tempDate.getDate().toString();
        if (parseInt(day)<10) {
            day = '0'+day;
        }
        let newDate = tempDate.getFullYear().toString()+'-'+month+'-'+day
        return newDate
    }

    getCharacterizationData() {
        let url = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/setupUpdate?sampleId='+this.sampleId+'&charType='+this.type + '&charId='+this.charId+'&charClassName='+this.charClassName);
        url.subscribe(
            data=>{
                this.data = data;
                this.dataTrailer = JSON.parse(JSON.stringify(this.data));
                this.data.characterizationDate = this.formatDate(this.data.characterizationDate)
                this.setupData = [];
                this.setupData['assayTypesByCharNameLookup'] = data['assayTypesByCharNameLookup'];
                this.setupData['protocolLookup'] = data['protocolLookup'];
                this.setupData['charSourceLookup'] = data['charSourceLookup'];
                this.setupData['techniqueTypeLookup']=data['techniqueInstruments']['techniqueTypeLookup'];
                this.setupData['manufacturerLookup']=data['techniqueInstruments']['manufacturerLookup'];
            },
            error=> {
                console.log('error')
            }
        );
    }

    deleteCharacterization() {

    }

    resetCharacterization() {
        this.data = JSON.parse(JSON.stringify(this.dataTrailer));
        this.setupData['assayTypesByCharNameLookup'] = [];
        this.changeType(this.data.type)

    }

    submitCharacterization() {
        this.data.characterizationDate = new Date(this.data.characterizationDate+' 00:00');
        let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/saveCharacterization',this.data);
        url.subscribe(
            data=> {
                this.router.navigate( ['home/samples/characterization', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
            },
            error=> {
                console.log('error')
            }
        )
    }

}

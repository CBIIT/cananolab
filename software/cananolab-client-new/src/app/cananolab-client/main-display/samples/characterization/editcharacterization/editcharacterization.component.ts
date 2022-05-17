import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { timeout } from 'rxjs/operators';
import { url } from 'inspector';
import { APP_BASE_HREF } from '@angular/common';
import { threadId } from 'worker_threads';
import { timeStamp } from 'console';

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
    serverUrl = Properties.API_SERVER_URL;


    charClassName;
    charId;
    columnHeader;
    columnOrder;
    columnHeaderIndex;
    currentDropdownValues;
    currentField;
    currentFinding;
    currentFile;
    data;
    dataTrailer;
    findingIndex;
    fileIndex;
    instrument;
    instrumentTrailer;
    instrumentIndex;
    otherValue;
    propertiesLoaded;
    setupData;
    techniqueIndex;
    techniqueInstrument;
    theFile: File | null = null;
    type;

    constructor( private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
    }

  ngOnInit(): void {
    this.currentDropdownValues = {};
        this.route.params.subscribe(
            ( params: Params ) => {
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
                if (!this.charId) {
                    let url = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/setupAdd?sampleId='+this.sampleId+'&charType='+this.type);
                    url.subscribe(
                        data=>{
                            this.data = data;
                            this.data.name='';
                            this.data.assayType='';
                            this.data.characterizationDate=null,
                            this.setCharacterizationData();
                            if (this.data.type=='other') {
                                this.addOtherValue('type',this.data.type)
                            }

                        },
                        error=> {
                            console.log('error')
                        }
                    );
                }
                else {
                    let url = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/setupUpdate?sampleId='+this.sampleId+'&charType='+this.type + '&charId='+this.charId+'&charClassName='+this.charClassName);
                    url.subscribe(
                        data=>{
                            this.data = data;
                            this.propertiesLoaded=true;
                            this.setCharacterizationData();
                            console.log('am i here')
                        },
                        error=> {

                        });
                }

            }
        );
    }

    addFileForm() {
        this.currentFile={
            "title":"",
            "keywordStr":"",
            "type":"",
            "description":""
        }
        this.fileIndex=-1;
    };

    addFinding() {
        this.findingIndex=-1;
        setTimeout(function() {
            document.getElementById('findingsEditForm').scrollIntoView();
        },100);

        this.currentFinding={
            "columnHeaders":[],
            "dirty":1,
            "numberOfColumns":"",
            "numberOfRows":""
        };
    };

    addInstrument(index) {
        this.instrument={
            "modelName":"",
            "manufacturer":"",
            "type":""
        }
        this.instrumentIndex=-1;
    };

    // set pointer fields to old values when adding other //
    addOtherValue(field,currentValue) {
        console.log('am i here', field, currentValue)
        this.currentField='';
        this.currentDropdownValues[field]=currentValue;
        this.otherValue='';
        this.currentField=field;
    };

    addTechniqueInstrument() {
        this.instrumentIndex=null;
        this.techniqueIndex=-1;
        this.setupTechniqueInstrument();
    };

    cancelColumnForm() {
        this.columnHeaderIndex=null;
    };

    cancelColumnOrder() {
        this.columnOrder=null;
    }

    cancelFile() {
        this.fileIndex=null;
    };

    cancelFinding() {
        this.columnHeaderIndex=null;
        this.findingIndex=null;
        this.columnHeaderIndex=null;
        this.fileIndex=null;
    };

    cancelInstrument() {
        if (this.instrumentIndex>-1) {
            console.log(this.techniqueInstrument)
            this.techniqueInstrument.instruments[this.instrumentIndex]=JSON.parse(JSON.stringify(this.instrumentTrailer));
        }
        this.instrumentIndex=null;
    };

    cancelTechniqueInstrument() {
        this.instrumentIndex=null;
        this.techniqueIndex=null;
    };

    changeColumnType(value,isDropdown) {
        console.log(value,isDropdown)
        let url = this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/getColumnNameOptionsByType?columnType='+value+'&charName='+this.data.name+'&assayType=');
        url.subscribe(data=> {
            this.setupData.columnNameOptions=data;
            if (isDropdown) {
                this.columnHeader.columnName=null;
            }
        },
        error=> {

        })
    };

    changeInstrumentType(value) {
        this.getInstrumentTypes(value);
    };

    changeColumnName(value, isDropdown) {
        let url = this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/getColumnValueUnitOptions?columnName='+value+'&conditionProperty=null');
        url.subscribe(data=> {
            this.setupData.valueUnitOptions=data;
            if (isDropdown) {
                this.columnHeader.valueUnit=null;
            }
        },
        error=> {
            console.log('error')
        })
    };

    changeName(name) {
        this.data.assayType = '';
        let assayUrl = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/getAssayTypesByCharName?charName='+name);
        assayUrl.subscribe(
            data=> {
                this.data['assayTypesByCharNameLookup'] = data;
            },
            error=> {
                console.log('error')
            }
        );
        let charPropertiesUrl = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/getCharProperties?charName='+name);
        this.propertiesLoaded=null;
        charPropertiesUrl.subscribe(
            data=> {
                this.data['property'] = data;
                this.propertiesLoaded=true;
            },
            error=> {
                console.log('error')
            }
        );

    };

    changeType(type) {
        this.data.name='';
        this.data['assayTypesByCharNameLookup'] = [];

        let url = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/getCharNamesByCharType?charType='+type);
        url.subscribe(
            data=> {
                this.data.charNamesForCurrentType = data;
            },
            error=> {
                console.log('error')
            }
        )
    };

    deleteCharacterization() {
        if (confirm('Are you sure you wish to delete this characterization')) {
            let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/removeCharacterization',this.data);
            url.subscribe(data=> {
                this.router.navigate( ['home/samples/characterization', this.sampleId] );
            },
            error=> {
                console.log('error')
            })
        }
    };

    deleteFile() {

    }

    deleteFinding() {
        if (confirm('Are you sure you wish to delete this finding?')) {
            this.columnHeaderIndex=null;
            this.findingIndex=null;
            let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/removeFinding',this.currentFinding);
            url.subscribe(data=> {
                this.data=data;
                this.setCharacterizationData();
            },
            error=> {
                console.log('error')
            })
        }
    };

    deleteFindingRow(index) {
        if (confirm('Are you sure you wish to delete this finding row?')) {
            this.currentFinding.rows.splice([index],1);
        }
    };

    deleteInstrument() {
        if (confirm("Are you sure you want to delete this instrument?")) {
            this.techniqueInstrument.instruments.splice(this.instrumentIndex,1);
            this.instrumentIndex=null;
        }
    };

    deleteTechniqueInstrument() {
        if (confirm("Are you sure you want to delete this technique and instrument?")) {

            let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/removeExperimentConfig',this.techniqueInstrument);
            url.subscribe(data=> {
                this.data=data;
                this.dataTrailer=JSON.parse(JSON.stringify(this.data));
                this.setCharacterizationData();
            },
            error=> {

            });
            this.techniqueIndex=null;
            this.instrumentIndex=null;
        }
    };

    editColumnForm(column, index) {
        this.columnHeaderIndex=index;
        this.columnHeader=column;
        if (column.columnType) {
            this.changeColumnType(column.columnType,false);
            this.changeColumnName(column.columnName,false);
        }
        else {
        }
        console.log(this.columnHeaderIndex)
        this.columnHeader=JSON.parse(JSON.stringify(column));
    };

    editColumnOrder() {
        this.columnOrder=JSON.parse(JSON.stringify(this.currentFinding));
    };

    editFileForm(file,index) {
        this.currentFile=JSON.parse(JSON.stringify(file));
        this.fileIndex=index;
    };

    editFinding(index,finding) {
        this.columnOrder=null;
        this.currentFinding=JSON.parse(JSON.stringify(finding));
        this.findingIndex=index;
        setTimeout(function() {
            document.getElementById('findingsEditForm').scrollIntoView();
        },100);
    };

    editInstrument(instrument, index) {
        this.instrumentIndex=index;
        this.instrument=instrument;
        this.instrumentTrailer=JSON.parse(JSON.stringify(this.instrument));
    };

    editTechniqueInstrument(index, technique) {
        this.instrumentIndex=null;
        this.techniqueIndex=index;
        this.techniqueInstrument = JSON.parse(JSON.stringify(technique));
        this.techniqueInstrument.dirty=1;
        this.getInstrumentTypes(this.techniqueInstrument.techniqueType)
    };

    formatDate(date) {
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
    };

    getInstrumentTypes(value) {
        let url = this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/getInstrumentTypesByTechniqueType?techniqueType='+value);
        url.subscribe(data=> {
            this.setupData['instrumentTypeLookup']=data;
        },
        error=> {
            console.log('error')
        });
    };

    resetCharacterization() {
        this.data = JSON.parse(JSON.stringify(this.dataTrailer));
        this.data['assayTypesByCharNameLookup'] = [];
        this.changeType(this.data.type)

    };

    saveColumnForm() {
        this.currentFinding.columnHeaders[this.columnHeaderIndex]=this.columnHeader;
        this.columnHeaderIndex=null;
        this.fileIndex=null;
    };

    saveColumnOrder() {
        this.currentFinding=JSON.parse(JSON.stringify(this.columnOrder));
        let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/setColumnOrder',this.columnOrder);
        url.subscribe(data=> {
            this.currentFinding=data;
        },
        error=>{
            console.log('error')
        })
        this.columnOrder=null;

    }

    saveFile() {
        if (this.fileIndex==-1) {
            console.log('push file')
        }
        else {

        }
        this.fileIndex=null;
    }

    saveFinding() {
        this.currentFinding.dirty=1;
        if (this.findingIndex==-1) {
            this.data.finding.push(this.currentFinding);
        }
        else {
            this.data.finding[this.findingIndex]=JSON.parse(JSON.stringify(this.currentFinding));
        }
        let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/saveFinding',this.data);
        url.subscribe(data=> {
            this.data=data;
            this.setCharacterizationData();

        },
        error=> {
            console.log('error')
        })
        this.columnHeaderIndex=null;
        this.findingIndex=null;
        this.fileIndex=null;
    };

    saveInstrument() {
        if (this.instrumentIndex==-1) {
            this.techniqueInstrument.instruments.push(this.instrument);
        }
        this.instrumentIndex=null;
    };

    // save other value //
    saveOther(newItem: Object) {
        if (newItem['change'] && newItem['value']) {
            this.addDropdownItem(newItem['array'],newItem['value'])
            this.setValue(newItem['field'],newItem['value']);
        }
        else {
            this.setValue(newItem['field'],newItem['value']);
        }
    };

    saveTechniqueInstrument() {
        if (this.techniqueIndex==-1) {
            this.data.techniqueInstruments.experiments.push(this.techniqueInstrument);
            let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/saveExperimentConfig',this.data);

            // push technique, call save and overwrite this.data //
        }

        this.data.techniqueInstruments.experiments[this.techniqueIndex]=this.techniqueInstrument;
            let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/saveExperimentConfig',this.data);
            url.subscribe(
                data=> {
                    this.data=data;
                    this.setCharacterizationData();
                },
                error=> {
                    console.log('error');

                }
            )

        this.techniqueIndex=null;
        this.instrumentIndex=null;
    };

    setCharacterizationData() {
        this.dataTrailer = JSON.parse(JSON.stringify(this.data));
        this.data.characterizationDate = this.formatDate(this.data.characterizationDate)
        this.setupData = [];
        let url = this.httpClient.get(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/getDatumNumberModifier?columnName=Number%20Modifier');
        url.subscribe(data=> {
            this.setupData.datumNumberModifier=data;
            this.setupData.datumNumberModifier.splice(this.setupData.datumNumberModifier.indexOf('other'),1)
        },
        error=> {
            console.log('error')
        })
        this.setupData.instrumentTypeLookup = [];
    };

    setupDefaultDataSet() {
        this.dataTrailer = JSON.parse(JSON.stringify(this.data));
    };

    setupTechniqueInstrument() {
        this.techniqueInstrument = {
            "techniqueType":"",
            "dirty":1,
            "instruments":[]
        }
    };

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
    };

    updateRowsColumns() {
        let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/updateDataConditionTable',this.currentFinding);
        url.subscribe(data=> {
            this.currentFinding=data;
        },
        error=> {
            console.log('error')
        })
    };

    uploadFile(event) {
        this.theFile = event[0];
        console.log(this.theFile)
        // const tFile = value.target.files[0];
        // this.theFile.append('file', tFile, tFile.name);
        // console.log(this.theFile)
        // this.fileName=tFile.name;
    }
}

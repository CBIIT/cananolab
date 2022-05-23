import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { NavigationService } from '../../../../common/services/navigation.service';
import { ApiService } from '../../../../common/services/api.service';
@Component({
  selector: 'canano-editcharacterization',
  templateUrl: './editcharacterization.component.html',
  styleUrls: ['../../../../../btn-bravo-canano.scss','./editcharacterization.component.scss']
})
export class EditcharacterizationComponent implements OnInit {
    sampleId = Properties.CURRENT_SAMPLE_ID;
    sampleName;
    helpUrl = Consts.HELP_URL_SAMPLE_CHARACTERIZATION;
    toolHeadingNameManage = 'Sample ' + this.sampleName + ' Characterization';
    serverUrl = Properties.API_SERVER_URL;


    charClassName;
    charId;
    columnHeader;
    columnOrder;
    columnHeaderIndex;
    currentDropdownValues;
    currentFinding;
    currentFile;
    data;
    dataTrailer;
    findingIndex;
    fileIndex;
    instrument;
    instrumentTrailer;
    instrumentIndex;
    propertiesLoaded;
    setupData;
    techniqueIndex;
    techniqueInstrument;
    theFile:FormData=null;;
    fileName;
    type;

    constructor( private apiService:ApiService,private navigationService:NavigationService,private router: Router, private route: ActivatedRoute,private httpClient: HttpClient ){
    }

  ngOnInit(): void {
    this.navigationService.setCurrentSelectedItem(2);
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
                this.apiService.getSampleName(this.sampleId).subscribe(
                    data=>this.toolHeadingNameManage='Submit Publication for '+data['sampleName'])



                if (!this.charId) {
                    let url = this.httpClient.get(Properties.API_SERVER_URL + '/caNanoLab/rest/characterization/setupAdd?sampleId='+this.sampleId+'&charType='+this.type);
                    url.subscribe(
                        data=>{
                            Properties.SAMPLE_TOOLS = true;

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
                            Properties.SAMPLE_TOOLS = true;

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
            "uriExternal":false,
            "externalUrl":"",
            "title":"",
            "keywordsStr":"",
            "type":"",
            "description":"",
            "sampleId":this.sampleId,
            "uri":""
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
            "numberOfRows":"",
            "files":[]
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
        this.currentDropdownValues[field]=currentValue;
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

    deleteFile(file, fileIndex) {
        this.currentFinding['theFile']=file;
        if (confirm('Are you sure you wish to delete this file?')) {
            this.currentFinding.files.splice(fileIndex,1);
            this.currentFinding.dirty=1;
            this.currentFinding['theFileIndex']=fileIndex;
            let url = this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/removeFile',this.currentFinding)
            url.subscribe(data=> {
                this.currentFinding=data;
            },
            error=> {
                console.log('error')
            })
        }
        console.log(this.currentFinding)
        console.log(file,this.currentFinding, fileIndex)

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
        console.log(this.fileIndex)
        this.theFile.append('uriExternal',this.currentFile['uriExternal']);
        this.theFile.append('externalUrl',this.currentFile['externalUrl']);
        this.theFile.append('type',this.currentFile['type']);
        this.theFile.append('title',this.currentFile['title']);
        this.theFile.append('keywordsStr',this.currentFile['keywordsStr']);
        this.theFile.append('description',this.currentFile['description']);
        console.log(this.currentFile)
        let uploadUrl=this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/core/uploadFile',this.theFile);
        uploadUrl.subscribe(data=> {
            if (this.fileIndex==-1) {
                this.currentFinding.files.push(data);
            }
            else {
                console.log(this.currentFinding)
                this.currentFinding.files[this.fileIndex]=data;
            }
            console.log(this.theFile)
            this.currentFinding['dirty']=1;
            this.currentFinding.theFile=this.currentFile;
            this.currentFinding['theFile']['uri']=data['fileName']
            // this.currentFinding['theFile']=data;
            let saveUrl=this.httpClient.post(Properties.API_SERVER_URL+'/caNanoLab/rest/characterization/saveFile',this.currentFinding) ;
            saveUrl.subscribe(data=> {
                this.currentFinding=data;
            },
            error=> {
                console.log('file save error')
            })
        },
        error=> {
            console.log('error')
        })
        if (this.fileIndex==-1) {
            this.currentFinding.files
            console.log('push file')
        }
        else {
            console.log('call upload')
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

    // splits keywords for experiments and configurations //
    splitKeywords(keywords) {
        if (keywords) {
            return keywords.split('\n')
        }
        else {
            return ''
        }
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
        // console.log(event.target.files);
        this.theFile = new FormData();
        const tFile = event.target.files.item(0);
        console.log(tFile)
        this.theFile.append('myFile', tFile, tFile.name);
        this.fileName=tFile.name;
        console.log(this.theFile)
        // this.currentFile['myFile']=file;
        // console.log(files.item(0))
        // const formData = new FormData();
        // let thisFile=files.item(0);
        // const formData: FormData = new FormData();
        // formData.append('fileKey',thisFile,thisFile.name);
        // console.log(formData);
        // this.currentFile['myFile']=files.item(0);
        // console.log(this.currentFile);
    }
}

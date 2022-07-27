import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Properties } from '../../../../../../assets/properties';
import { Consts } from '../../../../../constants';
import { NavigationService } from '../../../../common/services/navigation.service';
import { ApiService } from '../../../../common/services/api.service';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'canano-editcharacterization',
  templateUrl: './editcharacterization.component.html',
  styleUrls: ['../../../../../btn-bravo-canano.scss','./editcharacterization.component.scss']
})
export class EditcharacterizationComponent implements OnInit {
    sampleId = Properties.CURRENT_SAMPLE_ID;
    helpUrl = Consts.HELP_URL_SAMPLE_CHARACTERIZATION;
    toolHeadingNameManage;
    charClassName;
    charId;
    columnHeader;
    columnHeaderTrailer;
    columnOrder;
    columnHeaderIndex;
    currentDropdownValues;
    currentFinding;
    currentFile;
    data;
    dataTrailer;
    errors={};
    findingIndex;
    fileIndex;
    importingCSV=false;
    instrument;
    instrumentTrailer;
    instrumentIndex;
    message;
    propertiesLoaded;
    setupData;
    techniqueIndex;
    techniqueInstrument;
    theFile:FormData=null;;
    fileName;
    type;
    rowData;

    csvColumnMaxCount = 25; // Maximum number of columns allowed
    csvMaxNumberOfLines = 5000; // Maximum number of rows allowed
    csvMaxLenOfEntry = 200;
    runaway = 10240; // A counter used to prevent an endless loop if something goes wrong.  @TODO needs a better name
    csvDataColCount = 0;
    csvDataObj;
    csvDataRowCount;
    csvImportError = '';
    serverUrl = Properties.API_SERVER_URL;

    constructor( private httpClient:HttpClient,private apiService:ApiService,private navigationService:NavigationService,private router: Router, private route: ActivatedRoute){
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
                    data=>this.toolHeadingNameManage='Edit '+data['sampleName']+ ' Characterization')



                if (!this.charId) {
                    let url = this.apiService.doGet(Consts.QUERY_CHARACTERIZATION_SETUP_ADD,'sampleId='+this.sampleId+'&charType='+this.type);
                    url.subscribe(
                        data=>{
                            Properties.SAMPLE_TOOLS = true;
                            this.errors={};
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
                            this.errors=error;
                        }
                    );
                }
                else {
                    let url = this.apiService.doGet(Consts.QUERY_CHARACTERIZATION_SETUP_UPDATE,'sampleId='+this.sampleId+'&charType='+this.type+'&charId='+this.charId+'&charClassName='+this.charClassName);
                    url.subscribe(
                        data=>{
                            Properties.SAMPLE_TOOLS = true;
                            this.errors={};

                            this.data = data;
                            this.propertiesLoaded=true;
                            this.setCharacterizationData();
                        },
                        error=> {
                            this.errors=error;

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
            this.techniqueInstrument.instruments[this.instrumentIndex]=JSON.parse(JSON.stringify(this.instrumentTrailer));
        }
        this.instrumentIndex=null;
    };

    cancelTechniqueInstrument() {
        this.instrumentIndex=null;
        this.techniqueIndex=null;
    };

    changeColumnType(value,isDropdown) {
        let url = this.apiService.doGet(Consts.QUERY_CHARACTERIZATION_GET_COLUMN_NAME_OPTIONS_BY_TYPE,'columnType='+value+'&charName='+this.data.name+'&assayType=');
        url.subscribe(data=> {
            this.errors={};
            this.setupData.columnNameOptions=data;
            if (isDropdown) {
                this.columnHeader.columnName=null;
            }
        },
        error=> {
            this.errors=error;

        })
    };

    changeInstrumentType(value) {
        this.getInstrumentTypes(value);
    };

    changeColumnName(value, isDropdown) {
        let url = this.apiService.doGet(Consts.QUERY_CHARACTERIZATION_GET_COLUMN_VALUE_UNIT_OPTIONS,'columnName='+value+'&conditionProperty=null');
        url.subscribe(data=> {
            this.errors={};
            this.setupData.valueUnitOptions=data;
            if (isDropdown) {
                this.columnHeader.valueUnit=null;
            }
        },
        error=> {
            this.errors=error;
        })
    };

    changeName(name) {
        this.data.assayType = '';
        let assayUrl = this.apiService.doGet(Consts.QUERY_CHARACTERIZATION_GET_ASSAY_TYPES_BY_CHAR_NAME,'charName='+name);
        assayUrl.subscribe(
            data=> {
                this.errors={};
                this.data['assayTypesByCharNameLookup'] = data;
            },
            error=> {
                this.errors=error;
            }
        );
        let charPropertiesUrl = this.apiService.doGet(Consts.QUERY_CHARACTERIZATION_GET_CHAR_PROPERTIES,'charName='+name)
        this.propertiesLoaded=null;
        charPropertiesUrl.subscribe(
            data=> {
                this.errors={};
                this.data['property'] = data;
                this.propertiesLoaded=true;
            },
            error=> {
                this.errors=error;
            }
        );

    };

    changeType(type) {
        this.data.name='';
        this.data['assayTypesByCharNameLookup'] = [];
        let url = this.apiService.doGet(Consts.QUERY_CHARACTERIZATION_GET_CHAR_NAMES_BY_CHAR_TYPE,'?charType='+type)
        url.subscribe(
            data=> {
                this.data.charNamesForCurrentType = data;
            },
            error=> {
                this.errors=error;
            }
        )
    };

    deleteCharacterization() {
        if (confirm('Are you sure you wish to delete this characterization')) {
            let url = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_REMOVE,this.data);
            url.subscribe(data=> {
                this.errors={};
                this.router.navigate( ['home/samples/characterization', this.sampleId] );
            },
            error=> {
                this.errors=error;
            })
        }
    };

    deleteFile(file, fileIndex) {
        this.currentFinding['theFile']=file;
        if (confirm('Are you sure you wish to delete this file?')) {
            this.currentFinding.files.splice(fileIndex,1);
            this.currentFinding.dirty=1;
            this.currentFinding['theFileIndex']=fileIndex;
            let url = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_REMOVE_FILE,this.currentFinding);
            url.subscribe(data=> {
                this.errors={};
                this.currentFinding=data;
            },
            error=> {
                this.errors=error;
            })
        }


    }

    deleteFinding() {
        if (confirm('Are you sure you wish to delete this finding?')) {
            this.columnHeaderIndex=null;
            this.findingIndex=null;
            let url = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_REMOVE_FINDING,this.currentFinding);
            url.subscribe(data=> {
                this.errors={};
                this.data=data;
                this.setCharacterizationData();
            },
            error=> {
                this.errors=error;
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
            let url = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_REMOVE_EXPERIMENT,this.techniqueInstrument);
            url.subscribe(data=> {
                this.data=data;
                this.errors={};
                this.dataTrailer=JSON.parse(JSON.stringify(this.data));
                this.setCharacterizationData();
            },
            error=> {
                this.errors=error;

            });
            this.techniqueIndex=null;
            this.instrumentIndex=null;
        }
    };

    editColumnForm(column, index) {
        this.columnHeaderIndex=index;
        this.columnHeader=column;
        this.columnHeaderTrailer=JSON.parse(JSON.stringify(this.columnHeader));

        if (column.columnType) {
            this.changeColumnType(column.columnType,false);
            this.changeColumnName(column.columnName,false);
        }
        else {
        }
        this.columnHeader=JSON.parse(JSON.stringify(column));
    };

    editColumnOrder() {
        this.columnOrder=JSON.parse(JSON.stringify(this.currentFinding));
    };

    editFileForm(file,index) {
        this.currentFile=JSON.parse(JSON.stringify(file));
        this.fileIndex=index;
    };

    editFile(file,index) {
        this.fileIndex=index;
        this.currentFile=file;
        this.currentFinding['dirty']=true;
        this.currentFinding['theFileIndex']=index;
    }

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
        let url = this.apiService.doGet(Consts.QUERY_CHARACTERIZATION_GET_INSTRUMENT_TYPES_BY_TECHNIQUE,'techniqueType='+value);
        url.subscribe(data=> {
            this.errors={};
            this.setupData['instrumentTypeLookup']=data;
        },
        error=> {
            this.errors=error;
        });
    };

    importCSV(event) {
        let csvFile = event.target.files.item(0);
        let size= csvFile.size;
        this.dataReaderReadFile(0,size,csvFile);

    }

    dataReaderReadFile = function (opt_startByte, opt_stopByte,csvFile) {
        let reader = new FileReader();
        var that=this;
        reader.onloadend = function (evt) {
            that.csvDataObj = that.parseCsv(evt.target.result);

            if (that.csvDataObj === null) {
                alert('CSV import parse error: ' + that.csvImportError);
                return;
            }

            that.csvDataColCount = 0;
            that.csvDataRowCount = that.csvDataObj.length;
            for (var y = 0; y < that.csvDataRowCount; y++) {
                if (that.csvDataObj[y].length > that.csvDataColCount) {
                    that.csvDataColCount = that.csvDataObj[y].length;
                }
            }
            that.currentFinding.numberOfColumns = that.csvDataColCount;
            that.currentFinding.numberOfRows = that.csvDataRowCount;

            that.updateRowsColsForFinding();
        };

        reader.readAsBinaryString(csvFile.slice(0, opt_stopByte));

    };

    createArray(len) {
        let arr = new Array(len || 0),
            i = len;
        if (arguments.length > 1) {
            var args = Array.prototype.slice.call(arguments, 1);
            while (i--) arr[len - 1 - i] = this.createArray.apply(this, args);
        }
        return arr;
    }

    validateFindingCellInput(colType, cellData) {
        if ((colType === null) || (colType === undefined)) {
            return false;
        }
        return (
            (colType === 'datum') &&
            (cellData !== null) && (isNaN(cellData.replace(/(d|f)$/, '')))
        );
    }

    updateRowsColsForFinding = function () {
        this.badFindingCell = this.createArray(this.csvDataColCount, this.csvDataRowCount);
        let url = this.apiService.doPost(Consts.QUERY_CHARACTERIZTAION_UPDATE_FINDING,this.currentFinding);

        url.subscribe(data=> {
                data = data;
                if (data.rows[this.csvDataRowCount - 1] === undefined) {
                    this.csvDataRowCount = data.numberOfRows;
                }

                for (var y = 0; y < this.csvDataRowCount; y++) {

                    for (var x = 0; x < this.csvDataColCount; x++) {
                        // If the user has reduced the number of columns, make sure we don't try to update columns that no longer exist.
                        if ((data.rows[y].cells[x] !== null) && (data.rows[y].cells[x] !== undefined)) {
                            data.rows[y].cells[x].value = Object(this.csvDataObj[y][x]);
                            if (x < this.currentFinding.length) {
                                data.rows[y].cells[x].datumOrCondition = this.currentFinding.columnHeaders[x].columnType;
                            }
                            // When the column type is set or reset, check all cell contents for valid entries for each column, one row at a time.
                            if (x < this.currentFinding.columnHeaders.length) {
                                this.badFindingCell[x][y] = this.validateFindingCellInput(this.currentFinding.columnHeaders[x].columnType,
                                    data.rows[y].cells[x].value);

                            }
                            // If there are fewer column types/header set than there are columns.
                            // Data put in a cell with a column that does not have it's type set is never considered invalid.
                            else {
                                this.badFindingCell[x][y] = false;
                            }
                        }
                    }
                }

                // If there are already column headers set, preserve them.
                for (var colX = 0; colX < this.csvDataColCount; colX++) {
                    if ((this.currentFinding.columnHeaders[colX] !== null) && (this.currentFinding.columnHeaders[colX] !== undefined)) {
                        data.columnHeaders[colX] = this.currentFinding.columnHeaders[colX];
                    }
                 }
                this.currentFinding = data;
                console.log('complete')

        })
    };

    rendering() {
        console.log('test')
    }

    validateCsv = function (csv) {
        // Normalize line feeds
        let temp = (csv.replace(/\r\n/g, '\r').replace(/\n\r/g, '\r').replace(/\n/g, '\r')).split(/\r/);

        // Do we have too many rows?
        if (temp.length > this.csvMaxNumberOfLines) {
            this.csvImportError = 'Too many Lines (' + temp.length + ')';
            return false;
        }

        // Are any cells too long?
        // Determine length of longest cell entry
        let biggestLine = 0;
        for (let f0 = 0; f0 < temp.length; f0++) {
            if (biggestLine < temp[f0].length) {
                biggestLine = temp[f0].length;
            }
        }

        // If at least one entry is too long, set error and return false
        if (biggestLine > this.csvMaxLenOfEntry) {
            this.csvImportError = 'line(s) too long (' + biggestLine + ')';
            return false;
        }


        // Send each line to csv validation function.
        // Remove anything that is not a quote or a comma. That is all we need for validating csv.
        let regex = new RegExp('[^",]', 'g');
        for (let f = 0; f < temp.length; f++) {
            let csvString = temp[f].replace(regex, '');
            let isValid = this.validateCsvLine(csvString);
            if (!isValid) {
                return false;
            }
        }

        // Return true if: not too many rows, no row is too long, and csv format is correct.
        return true;
    };
    validateCsvLine(csvLine) {
        let inQ = false;
        let badData = false;
        for (let f = 0; f < csvLine.length; f++) {
            if (!inQ) {

                // A starting quote plus a nested quote (3 quotes)
                if ((csvLine.length <= (f + 2)) && csvLine[f] === '"' && csvLine[f + 1] === '"' && csvLine[f + 2] === '"') {
                    inQ = true;
                }

                // Two quotes, BUT not in a quote, and ends.
                else if ((csvLine.length <= (f + 1)) && csvLine[f] === '"' && csvLine[f + 1] === '"') {
                    badData = false;
                    break;
                } else if (csvLine[f] === '"') {
                    inQ = true;
                }
            } else {
                // An ending quote
                if (csvLine[f] === '"' && csvLine[f + 1] !== '"') {
                    inQ = false;
                } else if (csvLine[f] === '"' && csvLine[f + 1] === '"') {
                    f++;
                }
            }
        }

        // Are we still in a quote at the end
        if (inQ) {
            badData = true;
            this.csvImportError = 'csv validation error';
        }
        return (!badData);
    }

    qFix(input) {
        var output = '';
        for (var i = 0; i < input.length; ++i) {

            if (input.charCodeAt(i) === 226) {
                // Unicode double quote
                if (
                    (input.charCodeAt(i + 1) === 128 && input.charCodeAt(i + 2) === 157) ||
                    (input.charCodeAt(i + 1) === 128 && input.charCodeAt(i + 2) === 156)
                ) {
                    i += 2;
                    output += '"';
                }
                // Unicode single quote
                else if (input.charCodeAt(i + 1) === 128 && input.charCodeAt(i + 2) === 153) {
                    i += 2;
                    output += '\'';
                }

            } else if (input.charCodeAt(i) === 194 || input.charCodeAt(i) === 195) {
                var hexDigit0 = input.charCodeAt(i).toString(16);
                if (hexDigit0.length % 2) {
                    hexDigit0 = '0' + hexDigit0;
                }
                var hexDigit1 = input.charCodeAt(i + 1).toString(16);
                if (hexDigit1.length % 2) {
                    hexDigit1 = '0' + hexDigit1;
                }
                var hex = '%' + hexDigit0 + '%' + hexDigit1;
                let decoded = this.decode_utf8(hex);
                if (decoded === 'ERROR-ERROR') {
                    output = '';
                    return output;
                }
                output += decoded;
                i++;
            } else {
                output += input[i];
            }
        }
        return (output);
    };


    cleanCsvValue(val) {
        if (val.substr(0, 1) === '"') {
            val = val.substr(1);
        }
        if (val.substr(val.length - 1) === ',') {
            val = val.substr(0, val.length - 1);
        }
        if (val.substr(val.length - 1) === '"') {
            val = val.substr(0, val.length - 1);
        }

        val = val.replace(/""/g, '"');
        return val;
    };


    /**
     *
     * @param s
     * @returns {string}
     */
    decode_utf8(s) {
        var returnData = '';
        try {
            returnData = decodeURIComponent(s);
        } catch (e) {
            returnData = 'ERROR-ERROR'; // TODO  Make this a const
        }
        return returnData;
    }

    parseCsv(data) {
        if (!this.validateCsv(data)) {
            return null;
        }
        // Split on the CR or LF
        let dataLines = this.qFix(data.replace(/\r\n/g, '\r').replace(/\n\r/g, '\r').replace(/\n/g, '\r')).split(/\r/);
        let startCell = 1; //true
        let currentCell = '';
        let currentCellType = 0; // 0=unknown  1=comma no double quote  2=comma with double quote
        let i = 0;
        let csvData;
        let csvDataObj = [];

        for (let dataLine = 0; dataLine < dataLines.length && this.runaway > 0; dataLine++) {
            csvData = dataLines[dataLine];

            if (csvData.length < 1) {
                continue;
            }

            let lineOfValues = [];
            i = 0;
            while (i < csvData.length && this.runaway > 0) {
                let trailingCommas = [];
                trailingCommas = csvData.match(/(,+)$/g);
                if (trailingCommas !== null) {
                    let replacementStr = '';
                    for (let f = 0; f < trailingCommas[0].length; f++) {
                        replacementStr += ',""';
                    }
                    let re = new RegExp(trailingCommas[0] + '$');
                    csvData = csvData.replace(re, replacementStr);
                }
                // Determine cell type
                if (csvData.substr(i, 1) === '"') {
                    currentCellType = 2;
                } else {
                    currentCellType = 1;
                }

                if (currentCellType === 1) {
                    // Just grab to the first comma
                    currentCell = csvData.substr(i).match(/[^,]*,/);
                    if (currentCell !== null) {
                        currentCell = currentCell[0];
                        lineOfValues.push(this.cleanCsvValue(currentCell));
                    }
                    // No comma, we are at the end.
                    else {
                        currentCell = csvData.substr(i);
                        lineOfValues.push(this.cleanCsvValue(currentCell));
                    }
                    i += currentCell.length;
                } else if (currentCellType === 2) {
                    csvData = csvData.substr(i);
                    i = 0;
                    startCell = 1;
                    let charStatus = 0; // Nothing yet
                    let currentChar = '';
                    let currentNextChar = '';
                    let i1 = 0;

                    while (i1 < csvData.length) {
                        currentChar = csvData.substr(i1, 1);
                        if (i1 + 1 < csvData.length) {
                            currentNextChar = csvData.substr(i1 + 1, 1);
                        } else {
                            currentNextChar = '';
                        }
                        i1++;

                        // The first char
                        if (charStatus === 0 && startCell === 1) {
                            // Is it a quote (it should be)
                            if (currentChar === '"') {
                                charStatus = 1; // We have seen the first quote
                            }
                            startCell = 0; // No longer looking at the first char
                            currentChar = csvData.substr(i1, 1);
                            if (i1 + 1 < csvData.length) {
                                currentNextChar = csvData.substr(i1 + 1, 1);
                            } else {
                                currentNextChar = '';
                            }
                        } // END if (charStatus === 0 && startCell === 1)

                        // Not the first char
                        else if (startCell !== 1) {
                            // We are past the first quote
                            if (charStatus === 1) { // We have seen the first quote
                                // Check for two double quotes, this is a quote within a quoted cell - ignore it and go past it
                                if (currentChar === '"' && currentNextChar === '"') {
                                    i1 += 1;
                                }
                                // A quote here means the end
                                else if (currentChar === '"') {
                                    // Find the next comma or the end of the line.
                                    currentCell = csvData.substr(0, i1);
                                    csvData = csvData.substr(currentCell.length + 1);
                                    i1 = 0;
                                    startCell = 1; //true
                                    charStatus = 0;
                                    lineOfValues.push(this.cleanCsvValue(currentCell));
                                }
                            }

                        } // END else if( startCell !== 1)
                        this.runaway--;
                    }
                }
                this.runaway--;
            } // End while loop

            csvDataObj.push(lineOfValues);
            this.runaway--;

        } // End for loop

        // Check here for too may columns
        if (this.getMaxColumnCount(csvDataObj) > this.csvColumnMaxCount) {
            this.csvImportError = 'Too many columns (' + this.getMaxColumnCount(csvDataObj) + ')';
            return null;
        }
        let columnCount = this.getMaxColumnCount(csvDataObj);
        for (let f = 0; f < csvDataObj.length; f++) {
            while (csvDataObj[f].length < columnCount) {
                csvDataObj[f].push('');
            }
        }
        return csvDataObj;
    }

    getMaxColumnCount(csvData) {
        var columnCount = 0;
        for (var row = 0; row < csvData.length; row++) {
            if (columnCount < csvData[row].length) {
                columnCount = csvData[row].length;
            }
        }
        return columnCount;
    }

    resetCharacterization() {
        this.data = JSON.parse(JSON.stringify(this.dataTrailer));
        this.data['assayTypesByCharNameLookup'] = [];
        this.changeType(this.data.type)

    };

    resetColumnForm() {
        this.columnHeader=JSON.parse(JSON.stringify(this.columnHeaderTrailer));
    }

    saveColumnForm() {
        if (this.columnHeader['constantValue']!='') {
            this.currentFinding['rows'].forEach(row=> {
                row['cells'][this.columnHeaderIndex]['value']=this.columnHeader['constantValue'];
            });
        }
        this.currentFinding.columnHeaders[this.columnHeaderIndex]=this.columnHeader;
        this.columnHeaderIndex=null;
        this.fileIndex=null;
    };

    saveColumnOrder() {
        this.currentFinding=JSON.parse(JSON.stringify(this.columnOrder));
        let url = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_SET_COLUMN_ORDER,this.columnOrder);
        url.subscribe(data=> {
            this.errors={};
            this.currentFinding=data;
        },
        error=>{
            this.errors=error;
        })
        this.columnOrder=null;

    }

    saveFile() {
        if (!this.currentFile['uriExternal']) {
            if (this.theFile) {
                this.theFile.append('uriExternal',this.currentFile['uriExternal']);
                this.theFile.append('externalUrl',this.currentFile['externalUrl']);
                this.theFile.append('type',this.currentFile['type']);
                this.theFile.append('title',this.currentFile['title']);
                this.theFile.append('keywordsStr',this.currentFile['keywordsStr']);
                this.theFile.append('description',this.currentFile['description']);
                let uploadUrl = this.httpClient.post('/'+Consts.QUERY_UPLOAD_FILE,this.theFile);
                uploadUrl.subscribe(data=> {
                    if (this.fileIndex==-1) {
                        this.currentFinding.files.push(data);
                    }
                    else {
                        this.currentFinding.files[this.fileIndex]=data;
                    }
                    this.currentFinding['dirty']=1;
                    this.currentFinding.theFile=this.currentFile;
                    this.currentFinding['theFile']['uri']=data['fileName']
                    let saveUrl = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_SAVE_FILE,this.currentFinding);
                    saveUrl.subscribe(data=> {
                        this.errors={};
                        this.currentFinding=data;
                    },
                    error=> {
                        this.errors=error;
                    })
                },
                error=> {
                    this.errors=error;
                })
            }
            else {
                this.currentFinding['dirty']=1;
                this.currentFinding.theFile=this.currentFile;
                this.currentFinding['theFile']['uri']=this.currentFile['uri']
                let saveUrl = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_SAVE_FILE,this.currentFinding);
                saveUrl.subscribe(data=> {
                    this.errors={};
                    this.currentFinding=data;
                },
                error=> {
                    this.errors=error;
                })
            }


            if (this.fileIndex==-1) {
                this.currentFinding.files
            }
            else {
            }
        }
        else {
            this.currentFinding['theFile']=this.currentFile;
            let saveUrl = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_SAVE_FILE,this.currentFinding);
            saveUrl.subscribe(data=> {
                this.errors={};
                this.currentFinding=data;
            },
            error=> {
                this.errors=error;
            })
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
        let url = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_SAVE_FINDING,this.data);
        url.subscribe(data=> {
            this.errors={};

            this.data=data;
            this.setCharacterizationData();

        },
        error=> {
            this.errors=error;
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
            let url = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_SAVE_EXPERIMENT,this.data);
            // push technique, call save and overwrite this.data //
        }

        this.data.techniqueInstruments.experiments[this.techniqueIndex]=this.techniqueInstrument;
        let url = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_SAVE_EXPERIMENT,this.data);
        url.subscribe(
                data=> {
                    this.errors={};

                    this.data=data;
                    this.setCharacterizationData();
                },
                error=> {
                    this.errors=error;

                }
            )

        this.techniqueIndex=null;
        this.instrumentIndex=null;
    };

    setCharacterizationData() {
        this.dataTrailer = JSON.parse(JSON.stringify(this.data));
        this.data.characterizationDate = this.formatDate(this.data.characterizationDate)
        this.setupData = [];
        let url = this.apiService.doGet(Consts.QUERY_CHARACTERIZATION_GET_DATUM_NUMBER_MODIFIER,'columnName=Number%20Modifier');
        url.subscribe(data=> {
            this.errors={};

            this.setupData.datumNumberModifier=data;
            this.setupData.datumNumberModifier.splice(this.setupData.datumNumberModifier.indexOf('other'),1)
        },
        error=> {
            this.errors=error;
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
        let url = this.apiService.doPost(Consts.QUERY_CHARACTERIZATION_SAVE,this.data);
        url.subscribe(
            data=> {
                this.errors={};
                this.router.navigate( ['home/samples/characterization', Properties.CURRENT_SAMPLE_ID] );  // @FIXME  Don't hard code these
            },
            error=> {
                this.errors=error;
            }
        )
    };

    updateRowsColumns() {
        let url = this.apiService.doPost(Consts.QUERY_CHARACTERIZTAION_UPDATE_FINDING,this.currentFinding)
        url.subscribe(data=> {
            this.errors={};
            this.currentFinding=data;
        },
        error=> {
            this.errors=error;
        })
    };

    uploadFile(event) {
        this.theFile = new FormData();
        const tFile = event.target.files.item(0);
        console.log(typeof(tFile))
        console.log(tFile)
        this.theFile.append('myFile', tFile, tFile.name);
        this.fileName=tFile.name;
    }
}

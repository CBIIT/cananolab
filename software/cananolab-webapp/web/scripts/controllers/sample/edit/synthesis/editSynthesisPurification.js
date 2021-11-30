  'use strict';
  var app = angular.module('angularApp')
    .controller('EditSynthesisPurificationCtrl', function (sampleService, utilsService, navigationService, $anchorScroll, groupService, $rootScope, $scope, $http, $location, $filter, $modal, $routeParams) {
      $scope.tempShow = false;
      $scope.dropdowns = {};
      $scope.currentFile = {}; // current file being edited //
      $scope.fileArray = [];
      $scope.technique = []; // current inherent being edited //
      $scope.instrument = {}; // current instrument being edited //
      $scope.instrumentFormIndex = null; // current instrument being edited //
      $scope.techniqueFormIndex = null;
      $scope.purityEdit = 0;
      $scope.fileFormIndex = null;
      $scope.sampleId = $location.search()['sampleId'];
      $scope.sampleName = '';
      $scope.dataId = $location.search()['dataId'];
      $scope.sampleName = sampleService.sampleName($scope.sampleId);
      $scope.fileId = null;
      $scope.operands = ['='];
      $scope.otherInstrumentType='';  
      $scope.fObject = null;
      /* csv upload */
      var csvColumnMaxCount = 25; // Maximum number of columns allowed
      var csvMaxNumberOfLines = 5000; // Maximum number of rows allowed
      var csvMaxLenOfEntry = 200;
      var runaway = 10240; // A counter used to prevent an endless loop if something goes wrong.  @TODO needs a better name
      var csvDataColCount = 0;
      var csvDataObj;
      var csvDataRowCount;
      var csvImportError = '';


      // scrolls to section on page with provided id //
      $scope.scroll = function (id) {
        var old = $location.hash();
        $location.hash(id);
        $anchorScroll();
        $location.hash(old);
      };

      // initial setup for dropdowns //
      $http({
        method: 'GET',
        url: `/caNanoLab/rest/synthesisPurification/setup?sampleId=${$scope.sampleId}`
      }).
      then(function (data, status, headers, config) {
        data = data['data']
        $scope.dropdowns = data;
        $scope.loader = false;
      }).catch(function (data, status, headers, config) {
        data = data['data']
      });

      // function to return edit data for purification //
      if ($scope.dataId == -1) {
        $scope.purification = {
          "sampleId": $scope.sampleId,
          "designMethodDescription": "",
          "purityBeans":[]
        };
        $scope.purificationCopy = angular.copy($scope.purification);
      } else {
        $http({
          method: 'GET',
          url: `/caNanoLab/rest/synthesisPurification/setupEdit?sampleId=${$scope.sampleId}&dataId=${$scope.dataId}`
        }).
        then(function (data, status, headers, config) {
          data = data['data']
          $scope.purification = data;
          if (!$scope.purification['purityBeans']) {
            $scope.purification['purityBeans']=[];
          };       
          $scope.purificationCopy = angular.copy($scope.purification);  
          $scope.fileArray = angular.copy($scope.purification.fileElements)

          $scope.loader = false;
        }).catch(function (data, status, headers, config) {
          data = data['data']
        });
      };

      // add supplier to dropdown when selecting other //
      
      $scope.addType =function (otherInstrumentType) {
        $scope.instrumentTypes.push(otherInstrumentType);
        $scope.instrument.type=otherInstrumentType;
        $scope.otherInstrumentType='';
      };      

      // cancel file //
      $scope.cancelFile = function () {
        $scope.fileFormIndex = null;
      };

      // cancel technique //
      $scope.cancelTechnique = function (index, i) {
        console.log('dont do anything. Original technique stays as is');
        $scope.techniqueFormIndex = null;
      };

      $scope.cancelInstrument = function (index, i) {
        console.log('dont do anything. Original technique stays as is');
        $scope.instrumentFormIndex = null;
      };

      // delete for purification //
      $scope.deletePurification = function () {
        if (confirm("Are you sure you want to delete?")) {
          $http({
            method: 'POST',
            url: '/caNanoLab/rest/synthesisPurification/deletePurification',
            data: $scope.purification
          }).
          then(function (data, status, headers, config) {
            data = data['data']
            $location.search({
              'message': 'Synthesis Purification successfully deleted.',
              'sampleId': $scope.sampleId
            }).path('/editSynthesis').replace();
          }).
          catch(function (data, status, headers, config) {
            data = data['data']
          });
        };
      };

      // deletes technique from purification //
      $scope.deleteTechnique = function (index, parentIndex, technique) {
        $scope.purification['simpleExperimentBeans'].splice(index, 1);
      };

      // deletes instrument from technique //
      $scope.deleteInstrument = function (index, parentIndex, instrument) {
        $scope.technique['instruments'].splice(index, 1);
      };

      // gets abbreviation and desc for technique //
      $scope.getAbbreviationDescForTechnique = function () {
        $http({
          method: 'GET',
          url: `/caNanoLab/rest/synthesisPurification/findTechniqueByType?techniqueType=${$scope.technique.techniqueType}`
        }).
        then(function (data, status, headers, config) {
          data = data['data']
          $scope.techniqueAbbreviationDesc = data;
          $scope.technique['techniqueid'] = data['id'];
          $scope.loader = false;
        }).catch(function (data, status, headers, config) {
          data = data['data']
        });
      };

      // gets file conditions to make save file button enabled or disabled //
      $scope.getFileConditions = function () {
        let saveButtonDisabled = false;
        if ($scope.fileId) { // file is existing //
          if ($scope.currentFile.uriExternal) { // file is external //
            saveButtonDisabled = !$scope.currentFile.externalUrl || !$scope.currentFile.type || !$scope.currentFile.title ? true : false;
          } else { // file is internal. Do not have to check file //
            saveButtonDisabled = !$scope.currentFile.title || !$scope.currentFile.type ? true : false;
          };
        } else { // file is new //
          if ($scope.currentFile.uriExternal) { // file is external //
            saveButtonDisabled = !$scope.currentFile.externalUrl || !$scope.currentFile.type || !$scope.currentFile.title ? true : false;
          } else { // file is internal. Do not have to check file //
            saveButtonDisabled = !$scope.currentFile.title || !$scope.currentFile.type || !$scope.fileObject ? true : false;
          };
        };
        return saveButtonDisabled;
      };

      // open the file edit form //
      $scope.openFileForm = function (index, parentIndex, file) {
        $scope.uploadComplete = false;
        $scope.uploadError = false;
    
        $scope.fileObject = null;
        $('#uploadFile')[0].value = "";
        $scope.uploadError = null;
        $scope.fileId = null;
        $scope.fileErrorMessage = null;
        // uriExternal: true
        // externalUrl: https://google.com/fake.txt
        // type: document
        // title: test
        // keywordsStr: abc
        // description: 123
        // myFile: (binary)
        
        // uriExternal: false
        // type: document
        // title: title
        // keywordsStr: abc
        // description: 1234
        // myFile: (binary)
    
        if (index != -1) {
          $scope.fileFormIndex = index;
          $scope.fileId = file['id'];
          $scope.currentFile = { 
            "uri":file.uri, "uriExternal": file.uriExternal, 
            "id":file.id,
            "externalUrl":file.externalUrl,"type":file.type,
            "title":file.title, "description":file.description }
    
          console.log($scope.currentFile)
        }
        else {
          $scope.currentFile = {
            "uri": "", "uriExternal": false,
            "externalUrl": null, "type": "",
            "title": "", "description": "",
            "id":""
          }; // //
          $scope.fileFormIndex = index;
        };
      };
    

      // open the inherent function edit form //
      $scope.openTechniqueInstrumentsForm = function (index, parentIndex, technique) {
        console.log(index)
        if (index != -1) {
          $scope.technique = angular.copy(technique);
          if (!$scope.technique['instruments']) {
            $scope.technique['instruments']=[];
          }
          $scope.techniqueFormIndex = index;
        } else {
          $scope.technique = {
            "abbreviation": null,
            "description": null,
            "instruments": []
          }
          $scope.techniqueFormIndex = index;
        };
      };

      // open the inherent function edit form //
      $scope.openInstrumentForm = function (index, parentIndex, instrument) {
        $scope.getInstrumentTypes();
        if (index != -1) {
          $scope.instrument = angular.copy(instrument);
          $scope.instrumentFormIndex = index;
        } else {
          $scope.instrument = {
            "type": null,
            "manufacturer": null,
            "modelName": null
          }
          $scope.instrumentFormIndex = index;
        };
      };

      // get instrument types based on technique type //
      $scope.getInstrumentTypes = function () {
        $http({
          method: 'GET',
          url: `/caNanoLab/rest/synthesisPurification/getInstrumentTypesByTechniqueType?techniqueType=${$scope.technique.techniqueType}`
        }).
        then(function (data, status, headers, config) {
          data = data['data']
          $scope.instrumentTypes = data;
          $scope.loader = false;
        }).catch(function (data, status, headers, config) {
          data = data['data']
        });
      };

    // save file //
    $scope.saveFile = function (purity) {
      
      if (purity) { 
        $scope.purification['purityBeingEdited']=$scope.currentFinding; 
      }
      else {
        $scope.purification['purityBeingEdited']=null;
      }
      
      if (($scope.fileObject || $scope.fileForm.uploadedFile) && (!$scope.currentFile.uriExternal||!$scope.fileForm.uriExternal)) {
        console.log("I PASSED")
        $scope.uploadFile(purity);

        // watch upload complete //
        $scope.$watch('uploadComplete', function () {
          console.log($scope.uploadComplete)

          if ($scope.uploadComplete) {
            $scope.fileFormIndex = null;
            console.log('upload complete. Add to file')
          };
        });



        // watch upload error //
        $scope.$watch('uploadError', function () {
          if ($scope.uploadError) {
            $scope.fileErrorMessage = 'Error Uploading File';
          };
        });

      } else {
        console.log('just save. No new file or no file at all')
      }
    };

    // uploads file to server //
    $scope.uploadFile = function(purity) {
      console.log('i am uploading')
      var fd = new FormData(); // create new form data object //
      if (purity) {
        fd.append('file', $scope.fileForm.uploadedFile);
      }
      else {
        fd.append('file', $scope.fileObject);

      };
  /*
      fd.append('urlExternal', false);
      fd.append('type', 'image');
      fd.append('title', 'title');
  */
      $http.post('/caNanoLab/rest/core/uploadFile', fd, { withCredentials: false, headers: { 'Content-Type': undefined }, transformRequest: angular.identity }).
        then(function (data, status, headers, config) {
        var saveFileLocation = 'saveFile';
        if (purity) { 
          saveFileLocation='savePurityFile' 
          delete $scope.fileForm.uploadedFile;
        };
        data = data['data']
        $scope.uploadComplete = true;
          if ($scope.fileFormIndex==-1) {
            $scope.currentFile = purity?$scope.fileForm:$scope.currentFile;
            $scope.currentFile['uri'] = data['fileName']
            if (purity) { 
              $scope.currentFinding.files.push($scope.currentFile) 
            }
            else {
              $scope.fileArray.push($scope.currentFile);
            };
            
            $scope.purification['fileElements'] = $scope.fileArray;
            if (purity) { 
              $scope.purification['fileBeingEdited'] = $scope.currentFinding.files[$scope.currentFinding.files.length-1];
            }
            else {
              $scope.purification['fileBeingEdited'] = $scope.fileArray[$scope.fileArray.length-1];
            };

            $http.post('/caNanoLab/rest/synthesisPurification/'+saveFileLocation, $scope.purification).
            then(function(data) {
              data = data['data']
              console.log('done')
              $scope.purification = data;
              $scope.purificationCopy = angular.copy($scope.purification);
            }).
                catch (function(data) {
              console.log('caNanoLab/rest/synthesisPurification/saveFile ERROR data: ', data);
            });
  
            }
          else {
            console.log('i am here on line 340')

            $scope.currentFile['uri'] = data['fileName'];
            $scope.purification['fileElements'] = $scope.fileArray;
            $scope.purification['fileBeingEdited'] = $scope.fileArray[$scope.fileArray.length-1];
            $scope.fileArray[$scope.fileFormIndex]=$scope.currentFile;
            
            $http.post('/caNanoLab/rest/synthesisPurification/'+saveFileLocation, $scope.purification).
            then(function(data) {
              data = data['data']
              console.log('done')
              $scope.purification = data['data'];
              $scope.purificationCopy = angular.copy($scope.purification);              
            }).
                catch (function(data) {
              console.log('caNanoLab/rest/synthesisPurification/saveFile ERROR data: ', data);
            });          }
  
        }).
        catch(function (data, status, headers, config) {
          console.log('i am here on line 358')

          data = data['data']
          $scope.uploadError = true;
          $scope.fileErrorMessage = 'Error Uploading File';
        });
    };

      // save inherent function //
      $scope.openTechniqueInstruments = function (i) {
        if ($scope.techniqueFormIndex == -1) {
          if (!$scope.purification.instruments) {
            $scope.purification['instruments'] = [];
          };
          $scope.purification.instruments.push($scope.technique)
        } else {
          $scope.purification.instruments[$scope.inherentFunctionFormIndex] = $scope.technique;
        }
        $scope.inherentFunctionFormIndex = null;
      };

      // save technique //
      $scope.saveTechnique = function (technique) {
        if ($scope.techniqueFormIndex == -1) {
          $scope.purification['simpleExperimentBeans'].push($scope.technique)
        } else {
          $scope.purification['simpleExperimentBeans'][$scope.techniqueFormIndex] = technique;
          console.log($scope.techniqueFormIndex, $scope.purification['simpleExperimentBeans'][$scope.techniqueFormIndex])

        }
        $scope.techniqueFormIndex = null;

      };

      // save instrument //
      $scope.saveInstrument = function (instrument) {
        if ($scope.instrumentFormIndex == -1) {
          $scope.technique['instruments'].push($scope.instrument)
        } else {
          $scope.technique.instruments[$scope.instrumentFormIndex] = instrument;
        }
        $scope.instrumentFormIndex = null;
      };

      // submit the entire synthesis purification //
      $scope.savePurification = function () {
  
        $http({
          method: 'POST',
          url: '/caNanoLab/rest/synthesisPurification/submit',
          data: $scope.purification
        }).
        then(function (data, status, headers, config) {
          data = data['data']
          $scope.purification['fileElements'] = $scope.fileArray;
          $scope.purification['fileBeingEdited'] = $scope.fileArray[$scope.fileArray.length-1];
  
          $http({
            method: 'POST',
            url: '/caNanoLab/rest/synthesisPurification/saveFile',
            data: [$scope.purification]
          }).
          then(function () {
            data = data['data'];
    
          }).catch(function () {
            data = data['data'];
          })        
          $location.search({
            'message': 'Synthesis Purification successfully saved.',
            'sampleId': $scope.sampleId
          }).path('/editSynthesis').replace();
        }).
        catch(function (data, status, headers, config) {
          data = data['data']
          console.log('fail')
        });
      };

      // sets external url to true or false for current file //
      $scope.setFileType = (fileType) => {
        fileType ? $scope.currentFile.uriExternal = true : $scope.currentFile.uriExternal = false;
      };

      $scope.setPageTitle = () => $scope.dataId == -1 ? `Add ${$scope.sampleName.name} Synthesis - Purification` : `Edit ${$scope.sampleName.name} Synthesis - Purification`;




      // Finding section code //    
      $http({
        method: 'GET',
        url: 'rest/synthesisPurification/getDatumNumberModifier?name=Number%20Modifier'
      }).
      then(function (data, status) {
        data = data['data']
        $scope.operands = data;
        if (data.includes("other")) {
          var index = data.indexOf("other");
          data.splice(index, 1);
          $scope.operands = data;
        };

      });

      // opens new finding dialog //
      $scope.addNewFinding = function () {
        var old = $location.hash();
        $scope.purityEdit=0;
        $scope.currentFinding = {
          'columnHeaders': []
        };
        $scope.currentFinding.dirty = 1;
        $scope.updateFinding = 1;
        $scope.finding = {};
        $scope.scroll('editFindingInfo');
        $scope.isNewFinding = 1;
        $scope.currentFindingCopy = angular.copy($scope.currentFinding);

      };

      // open finding dialog with existing finding //
      $scope.updateExistingFinding = function (finding) {
        $scope.purityEdit=1;
        var old = $location.hash();
        $scope.updateFinding = 1;
        $scope.currentFinding = finding;
        checkAllFindingCells();
        $scope.scroll('editFindingInfo');
        $scope.isNewFinding = 0;
        $scope.currentFinding.dirty = 1;
        $scope.currentFindingCopy = angular.copy(finding);
      };

      // updates rows and columns and runs rest call update //
      // Check that input is valid for it's column type.
      $scope.updateRowsColsForFinding = function () {
        $scope.loader = true;
        $scope.badFindingCell = createArray(csvDataColCount, csvDataRowCount);

        $http({
          method: 'POST',
          url: '/caNanoLab/rest/synthesisPurification/newPurity',
          data: $scope.currentFinding
        }).
        then(function (data, status, headers, config) {
          data = data['data']
          if (data.rows[csvDataRowCount - 1] === undefined) {
            csvDataRowCount = data.numberOfRows;
          }

          for (var y = 0; y < csvDataRowCount; y++) {

            for (var x = 0; x < csvDataColCount; x++) {
              // If the user has reduced the number of columns, make sure we don't try to update columns that no longer exist.
              if ((data.rows[y].cells[x] !== null) && (data.rows[y].cells[x] !== undefined)) {
                data.rows[y].cells[x].value = Object(csvDataObj[y][x]);
                if (x < $scope.currentFinding.length) {
                  data.rows[y].cells[x].datumOrCondition = $scope.currentFinding.columnHeaders[x].columnType;
                }
                // When the column type is set or reset, check all cell contents for valid entries for each column, one row at a time.
                if (x < $scope.currentFinding.columnHeaders.length) {
                  $scope.badFindingCell[x][y] = validateFindingCellInput($scope.currentFinding.columnHeaders[x].columnType,
                    data.rows[y].cells[x].value);
                }
                // If there are fewer column types/header set than there are columns.
                // Data put in a cell with a column that does not have it's type set is never considered invalid.
                else {
                  $scope.badFindingCell[x][y] = false;
                }
              }
            }
          }

          // If there are already column headers set, preserve them.
          for (var colX = 0; colX < csvDataColCount; colX++) {
            if (($scope.currentFinding.columnHeaders[colX] !== null) && ($scope.currentFinding.columnHeaders[colX] !== undefined)) {
              data.columnHeaders[colX] = $scope.currentFinding.columnHeaders[colX];
            }
          }

          $scope.loader = false;
          $scope.currentFinding = data;

        }).
        catch(function (data, status, headers, config) {
          data = data['data']
          $scope.loader = false;
        });
      };

      // opens column form to change properties for column //
      $scope.openColumnForm = function (cell) {
        console.log('this is the cell ' + cell);
        $scope.findingsColumn = cell;
        $scope.columnForm = 1;

        $scope.findingsColumnCopyForRestore = {
          "columnName": "Column 1",
          "conditionProperty": null,
          "valueType": null,
          "valueUnit": null,
          "columnType": null,
          "name": "Column 1",
          "constantValue": "",
          "columnOrder": 1,
          "createdDate": null
        };

        $scope.findingsColumnCopy = angular.copy($scope.findingsColumn);

        if ($scope.findingsColumn.columnType) {
          $scope.onColumnTypeDropdownChange(1);
        }
      };

      // In the HTML the user is limited only numbers, and in a range from 1 to column count.
      // Here we need to add the limitation of, no duplicate numbers.
      $scope.columnOrderChanged = function (numberInput, index) {
        $scope.disableChangeColumnOrder = false;
        for (let i0 = 0; i0 < ($scope.currentFinding.columnHeaders.length - 1); i0++) {
          for (let i1 = (i0 + 1); i1 < $scope.currentFinding.columnHeaders.length; i1++) {
            if ($scope.currentFinding.columnHeaders[i0].columnOrder === $scope.currentFinding.columnHeaders[i1].columnOrder) {
              $scope.disableChangeColumnOrder = true;
              return;
            }
          }
        }
      };


      /**
       * Called when Finding cell input changes.
       *
       * @param textInput   Text input from the HTML.
       */
      $scope.currentFindingCellChanged = function (textInput) {
        let xy = textInput.id.split(":");
        $scope.badFindingCell[xy[0]][xy[1]] = validateFindingCellInput($scope.currentFinding.columnHeaders[xy[0]].columnType, textInput.value);
      };

      /**
       * Check for valid cell data for column type
       * @param colType
       * @param cellData
       * @returns True on bad cell data for column type.
       */
      function validateFindingCellInput(colType, cellData) {
        if ((colType === null) || (colType === undefined)) {
          return false;
        }
        return (
          (colType === 'datum') &&
          (cellData !== null) && (isNaN(cellData.replace(/(d|f)$/, '')))
        );
      }

      /**
       * Check each cell for valid data for column type and set status in $scope.badFindingCell array.
       */
      function checkAllFindingCells() {
        let rowCount = $scope.currentFinding.rows.length;
        if (rowCount > 0) {
          let cellCount = $scope.currentFinding.rows[0].cells.length;
          $scope.badFindingCell = createArray(cellCount, rowCount);
          for (let y = 0; y < rowCount; y++) {
            for (let x = 0; x < cellCount; x++) {
              $scope.badFindingCell[x][y] = validateFindingCellInput($scope.currentFinding.rows[y].cells[x].datumOrCondition,
                $scope.currentFinding.rows[y].cells[x].value);
            }
          }
        }
      }


      /**
       * Create multi dimensional array.
       * @param len  The length of one or more dimensions of array
       * @returns {any[]}  The array
       */
      function createArray(len) {
        let arr = new Array(len || 0),
          i = len;
        if (arguments.length > 1) {
          var args = Array.prototype.slice.call(arguments, 1);
          while (i--) arr[len - 1 - i] = createArray.apply(this, args);
        }
        return arr;
      }


      /**
       * This is called when a file in the users local machine is selected for csv upload.
       *
       * @param v File data.
       */
      $scope.fileNameChanged = function (v) {
        dataReaderReadFile(0, v[0].size);
      };


      /**
       * Called by the call back, get the file data here.
       *
       * @param opt_startByte
       * @param opt_stopByte
       */
      var dataReaderReadFile = function (opt_startByte, opt_stopByte) {
        let files = document.getElementById('csvFile').files;
        let reader = new FileReader();
        reader.onloadend = function (evt) {
          csvDataObj = parseCsv(evt.target.result);

          // Did we get a parse error
          if (csvDataObj === null) {
            console.catch('CSV import parse error: ', csvImportError);
            alert('CSV import parse error: ' + csvImportError);
            return;
          }

          // Get dimensions of csv data.
          csvDataColCount = 0;
          csvDataRowCount = csvDataObj.length;
          for (var y = 0; y < csvDataRowCount; y++) {
            if (csvDataObj[y].length > csvDataColCount) {
              csvDataColCount = csvDataObj[y].length;
            }
          }
          $scope.currentFinding.numberOfColumns = csvDataColCount;
          $scope.currentFinding.numberOfRows = csvDataRowCount;

          $scope.updateRowsColsForFinding();
        };
        reader.readAsBinaryString(files[0].slice(0, files[0].size));
      };


      /**
       * Return true if: not too many rows, no row is too long, and csv format is correct.
       *
       * @param csv
       * @returns {boolean}
       */
      let validateCsv = function (csv) {
        // Normalize line feeds
        let temp = (csv.replace(/\r\n/g, '\r').replace(/\n\r/g, '\r').replace(/\n/g, '\r')).split(/\r/);

        // Do we have too many rows?
        if (temp.length > csvMaxNumberOfLines) {
          csvImportError = 'Too many Lines (' + temp.length + ')';
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
        if (biggestLine > csvMaxLenOfEntry) {
          console.catch('ERROR line(s) too long (' + biggestLine + ')');
          csvImportError = 'line(s) too long (' + biggestLine + ')';
          return false;
        }


        // Send each line to csv validation function.
        // Remove anything that is not a quote or a comma. That is all we need for validating csv.
        let regex = new RegExp('[^",]', 'g');
        for (let f = 0; f < temp.length; f++) {
          let csvString = temp[f].replace(regex, '');
          let isValid = validateCsvLine(csvString);
          if (!isValid) {
            return false;
          }
        }

        // Return true if: not too many rows, no row is too long, and csv format is correct.
        return true;
      };


      /**
       * Check for correctly nested quotes.
       *
       * @param csvLine
       * @returns {boolean}
       */
      function validateCsvLine(csvLine) {
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
          csvImportError = 'csv validation error';
        }
        return (!badData);
      }


      /**
       * Convert csv data to javascript array.
       *
       * @param data
       * @returns {*}   javascript array.
       */
      function parseCsv(data) {
        if (!validateCsv(data)) {
          return null;
        }

        // Split on the CR or LF
        let dataLines = qFix(data.replace(/\r\n/g, '\r').replace(/\n\r/g, '\r').replace(/\n/g, '\r')).split(/\r/);
        let startCell = 1; //true
        let currentCell = '';
        let currentCellType = 0; // 0=unknown  1=comma no double quote  2=comma with double quote
        let i = 0;
        let csvData;
        let csvDataObj = [];

        for (let dataLine = 0; dataLine < dataLines.length && runaway > 0; dataLine++) {
          csvData = dataLines[dataLine];

          if (csvData.length < 1) {
            continue;
          }

          let lineOfValues = [];
          i = 0;
          while (i < csvData.length && runaway > 0) {
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
                lineOfValues.push(cleanCsvValue(currentCell));
              }
              // No comma, we are at the end.
              else {
                currentCell = csvData.substr(i);
                lineOfValues.push(cleanCsvValue(currentCell));
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
                      lineOfValues.push(cleanCsvValue(currentCell));
                    }
                  }

                } // END else if( startCell !== 1)
                runaway--;
              }
            }
            runaway--;
          } // End while loop

          csvDataObj.push(lineOfValues);

          runaway--;

        } // End for loop

        // Check here for too may columns
        if (getMaxColumnCount(csvDataObj) > csvColumnMaxCount) {
          csvImportError = 'Too many columns (' + getMaxColumnCount(csvDataObj) + ')';
          return null;
        }
        let columnCount = getMaxColumnCount(csvDataObj);
        for (let f = 0; f < csvDataObj.length; f++) {
          while (csvDataObj[f].length < columnCount) {
            csvDataObj[f].push('');
          }
        }
        return csvDataObj;
      }

      /**
       * Returns number of columns, it is possible for csv data to have inconsistent column count for rows
       * @param csvData
       * @returns {number}  The column count for the row(s) with the most columns.
       */
      function getMaxColumnCount(csvData) {
        var columnCount = 0;
        for (var row = 0; row < csvData.length; row++) {
          if (columnCount < csvData[row].length) {
            columnCount = csvData[row].length;
          }
        }
        return columnCount;
      }


      /**
       * Clean up Unicode type quotes and some UTF-8 chars
       *
       * @param input
       * @returns {string}
       */
      function qFix(input) {
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
            let decoded = decode_utf8(hex);
            if (decoded === 'ERROR-ERROR') {
              console.log('ERROR ');
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


      function cleanCsvValue(val) {
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
      function decode_utf8(s) {
        var returnData = '';
        try {
          returnData = decodeURIComponent(s);
        } catch (e) {
          returnData = 'ERROR-ERROR'; // TODO  Make this a const
          console.log('ERROR: ', e);
        }
        return returnData;
      }


      // close column form without saving //
      $scope.closeColumnForm = function () {
        angular.copy($scope.findingsColumnCopy, $scope.findingsColumn);
        $scope.columnForm = 0;
      };

      // close column form with saving //
      $scope.saveColumnForm = function () {
        $scope.columnForm = 0;

        var columnIndex = 0;
        if ($scope.findingsColumn.columnOrder != null) {
          columnIndex = parseInt($scope.findingsColumn.columnOrder) - 1;
          for (var x = 0; x < $scope.currentFinding.rows.length; x++) {
            var curCell = $scope.currentFinding.rows[x].cells[columnIndex];
            if ($scope.findingsColumn.constantValue != '') {
              curCell.value = $scope.findingsColumn.constantValue;
            }
          };
          var headerName = $scope.findingsColumn.name;

          if ($scope.findingsColumn.valueType != null) {
            headerName = headerName + ' (' + $scope.findingsColumn.valueType + ')';
          }
          $scope.currentFinding.columnHeaders[columnIndex].name = headerName;

        }

        // Check here for valid cell data with the new column type.
        initDatumOrCondition();
        checkAllFindingCells();

      };

      function initDatumOrCondition() {
        var rowCount = $scope.currentFinding.rows.length;
        var cellCount = $scope.currentFinding.rows[0].cells.length;
        for (var y = 0; y < rowCount; y++) {
          for (var x = 0; x < cellCount; x++) {
            $scope.currentFinding.rows[y].cells[x].datumOrCondition = $scope.currentFinding.columnHeaders[x].columnType;
          }
        }
      }



      // remove column data //
      $scope.removeColumnForm = function () {
        resetColumnConfirmDialog = $modal.open({
          templateUrl: 'views/sample/view/dataColumnHeaderReset.html',
          controller: 'DataColumnHeaderResetCtrl',
          size: 'sm',
          resolve: {

            col: function () {
              return $scope.findingsColumn;
            }
          }
        });

        resetColumnConfirmDialog.result.then(function (closeType) {
          if (closeType) {
            angular.copy($scope.findingsColumnCopyForRestore, $scope.findingsColumn);
            $scope.columnForm = 0;
          }
        });
      };

      // opens column form to change order for columns. Does not actually order columns //
      $scope.openColumnOrderForm = function () {
        $scope.columnOrder = 1;
      };

      // called when columnType dropdown is changed on column form //
      $scope.onColumnTypeDropdownChange = function (newOpen) {
        $http({
          method: 'GET',
          url: '/caNanoLab/rest/synthesisPurification/getColumnNameOptionsByType?columnType=' + $scope.findingsColumn.columnType + '&charName=' + $scope.purification.name + '&assayType=' + $scope.purification.assayType
        }).
        then(function (data, status, headers, config) {
          data = data['data']
          $scope.columnNameLookup = data;
          $scope.loader = false;
        }).
        catch(function (data, status, headers, config) {
          data = data['data']
          $scope.loader = false;
        });

        if (newOpen) {
          $scope.getColumnValueUnitOptions();
        };
      };

      // gets column value unit options based on  name and condition //
      $scope.getColumnValueUnitOptions = function () {
        $http({
          method: 'GET',
          url: '/caNanoLab/rest/synthesisPurification/getColumnValueUnitOptions?columnName=' + $scope.findingsColumn.name + '&conditionProperty=' + $scope.findingsColumn.conditionProperty
        }).
        then(function (data, status, headers, config) {
          data = data['data']
          $scope.valueUnitsLookup = data;
          $scope.loader = false;
        }).
        catch(function (data, status, headers, config) {
          data = data['data']
          $scope.loader = false;
        });
      };

      // called when columnType dropdown is changed on column form //
      $scope.onColumnNameDropdownChange = function () {
        $scope.getColumnValueUnitOptions();
        $http({
          method: 'GET',
          url: '/caNanoLab/rest/synthesisPurification/getConditionPropertyOptions?columnName=' + $scope.findingsColumn.name
        }).
        then(function (data, status, headers, config) {
          data = data['data']
          $scope.conditionTypeLookup = data;
          $scope.loader = false;
        }).
        catch(function (data, status, headers, config) {
          data = data['data']
          $scope.loader = false;
        });
      };

      // sets the column order //
      $scope.updateColumnOrder = function () {
        $scope.loader = true;


        $http({
          method: 'POST',
          url: '/caNanoLab/rest/synthesisPurification/setColumnOrder',
          data: $scope.currentFinding
        }).
        then(function (data, status, headers, config) {
          data = data['data']
          $scope.loader = false;
          $scope.currentFinding = data;
          $scope.columnOrder = 0;

          initDatumOrCondition();
          checkAllFindingCells();
        }).
        catch(function (data, status, headers, config) {
          data = data['data']
          $scope.loader = false;
          $scope.columnOrder = 0;
        });
      };

      // saves finding info //
      $scope.saveFindingInfo = function () {
        $scope.loader = true;
        if ($scope.isNewFinding) {
          $scope.purification.purityBeans.push($scope.currentFinding);
        };

        for (var x = 0; x < $scope.purification.purityBeans.length; x++) {
          if ($scope.purification.purityBeans[x].id == $scope.currentFinding.id) {
            $scope.purification.purityBeans.splice(x, 1);
            $scope.purification.purityBeans.push($scope.currentFinding);
            break;
          }
        };


        let haveDatum = false;
        for (let i0 = 0; i0 < $scope.purification.purityBeans.length; i0++) {
          for (let i1 = 0; i1 < $scope.purification.purityBeans[i0].columnHeaders.length; i1++) {
            if ($scope.purification.purityBeans[i0].columnHeaders[i1].columnType === 'datum') {
              haveDatum = true;
              break;
            }
          }
          if (haveDatum) {
            break;
          }
        }

        if (!haveDatum) {
          resetColumnConfirmDialog = $modal.open({
            templateUrl: 'views/sample/view/noDatumColumn.html',
            controller: 'NoDatumColumnCtrl',
            size: 'sm',
          });

          $scope.loader = false;
          return;
        }
        $scope.purityUrl='/caNanoLab/rest/synthesisPurification/createPurity' 
        $scope.purityData=$scope.purification;

        if ($scope.purityEdit) {
          delete $scope.purification.errors;
          $scope.purityData = $scope.currentFinding;
          $scope.purityUrl = '/caNanoLab/rest/synthesisPurification/updatePurity'
        };

        $http({
          method: 'POST',
          url: $scope.purityUrl,
          data: $scope.purityData
        }).
        then(function (data, status, headers, config) {
          data = data['data']

          $scope.saveButton = "Update";
          $scope.loader = false;
          $scope.purification = data;
        }).
        catch(function (data, status, headers, config) {
          data = data['data']
          $scope.loader = false;
        });
        $scope.updateFinding = 0;
      };

      // removes finding info //
      $scope.removeFindingInfo = function () {
        if (confirm("Delete the Finding?")) {
          $scope.loader = true;
          $http({
            method: 'POST',
            url: '/caNanoLab/rest/synthesisPurification/deletePurity',
            data: $scope.currentFinding
          }).
          then(function (data, status, headers, config) {
            data = data['data']
            $scope.loader = false;
            $scope.purification = data;
          }).
          catch(function (data, status, headers, config) {
            data = data['data']
            $scope.loader = false;
          });
          $scope.updateFinding = 0;
        }
      };

      $scope.cancelFindingInfo = function () {
        $scope.updateFinding = 0;
        angular.copy($scope.currentFindingCopy, $scope.currentFinding);
      };

      // deletes data and condition row //
      $scope.deleteDataConditionRow = function (row) {
        $scope.currentFinding.rows.splice($scope.currentFinding.rows.indexOf(row), 1);
      };

      // save characterization record. //
      $scope.save = function () {
        $scope.loader = true;
        $http({
          method: 'POST',
          url: '/caNanoLab/rest/synthesisPurification/saveCharacterization',
          data: $scope.purification
        }).
        then(function (data, status, headers, config) {
          data = data['data']
          $location.path("/editCharacterization").search({
            'sampleId': $scope.sampleId,
            'charMessage': 'Characterization Saved'
          }).replace();
          $scope.loader = false;
          $scope.sampleMessage = 'Characterization Saved';
        }).
        catch(function (data, status, headers, config) {
          data = data['data']
          $scope.loader = false;
        });
      };

      // remove characterization record. //
      $scope.remove = function () {
        $scope.loader = true;
        $http({
          method: 'POST',
          url: '/caNanoLab/rest/synthesisPurification/removeCharacterization',
          data: $scope.purification
        }).
        then(function (data, status, headers, config) {
          data = data['data']
          $scope.loader = false;
          $location.path("/editCharacterization").search({
            'sampleId': $scope.sampleId
          }).replace();
        }).
        catch(function (data, status, headers, config) {
          data = data['data']
          $scope.loader = false;
        });
      };

      // reset form //
      $scope.resetForm = function () {
        $scope.purification = angular.copy($scope.purificationCopy);
        $scope.domainFileUri = "";
        $scope.updateExperimentConfig = 0;
      };

      /* File Section */
      $scope.onFileSelect = function ($files) {
        $scope.selectedFiles = [];
        $scope.selectedFiles = $files;


        if ($scope.selectedFiles != null && $scope.selectedFiles.length > 0)
          $scope.selectedFileName = $scope.selectedFiles[0].name;

        $scope.dataUrls = [];
        for (var i = 0; i < $files.length; i++) {
          var $file = $files[i];
          if ($scope.fileReaderSupported && $file.type.indexOf('image') > -1) {
            var fileReader = new FileReader();
            fileReader.readAsDataURL($files[i]);
            var loadFile = function (fileReader, index) {
              fileReader.onload = function (e) {
                $timeout(function () {
                  $scope.dataUrls[index] = e.target.result;
                });
              }
            }(fileReader, i);
          }
        }
      };

      $scope.editFile = function (fileId) {
        $scope.selectedFileName = '';
        for (var k = 0; k < $scope.currentFinding.files.length; ++k) {

          var element = $scope.currentFinding.files[k];
          if (element.id == fileId) {
            $scope.fileForm.externalUrl = element.externalUrl;
            $scope.fileForm.uri = element.uri;
            $scope.fileForm.uriExternal = element.uriExternal;
            $scope.fileForm.type = element.type;
            $scope.fileForm.title = element.title;
            $scope.fileForm.keywordsStr = element.keywordsStr;
            $scope.fileForm.description = element.description;
            $scope.fileForm.id = element.id;
            $scope.fileForm.createdBy = element.createdBy;
            $scope.fileForm.createdDate = element.createdDate;

            $scope.addNewFile = true;


            if ($scope.fileForm.externalUrl != null && $scope.fileForm.externalUrl != '') {
              $scope.externalUrlEnabled = true;
              $scope.fileForm.uriExternal = 'true';
            } else {
              $scope.externalUrlEnabled = false;
              $scope.fileForm.uriExternal = 'false';
            }

            break;
          }
        }
      }

      $scope.removeFile = function (fileId) {
        if (confirm("Are you sure you want to delete the File?")) {
          $scope.loader = true;

          for (var k = 0; k < $scope.currentFinding.files.length; ++k) {
            var element = $scope.currentFinding.files[k];
            if (element.id == $scope.fileForm.id) {
              $scope.currentFinding.files.splice(k, 1);
              $scope.currentFinding.theFileIndex = k;
            }
          }
          //$scope.currentFinding.files = $scope.files;

          if ($scope.currentFinding.theFile == null) {
            $scope.currentFinding.theFile = {};
          }

          $scope.currentFinding.theFile.externalUrl = $scope.fileForm.externalUrl;
          $scope.currentFinding.theFile.uri = $scope.fileForm.uri;
          $scope.currentFinding.theFile.uriExternal = $scope.fileForm.uriExternal;
          $scope.currentFinding.theFile.type = $scope.fileForm.type;
          $scope.currentFinding.theFile.title = $scope.fileForm.title;
          $scope.currentFinding.theFile.keywordsStr = $scope.fileForm.keywordsStr;
          $scope.currentFinding.theFile.description = $scope.fileForm.description;
          $scope.currentFinding.theFile.id = $scope.fileForm.id;
          $scope.currentFinding.theFile.theAccess = $scope.fileForm.theAccess;
          $scope.currentFinding.theFile.isPublic = $scope.fileForm.isPublic;
          $scope.currentFinding.theFile.createdBy = $scope.fileForm.createdBy;
          $scope.currentFinding.theFile.createdDate = $scope.fileForm.createdDate;

          if ($scope.sampleId != null) {
            $scope.currentFinding.theFile.sampleId = $scope.sampleId;
          }


          $http({
            method: 'POST',
            url: '/caNanoLab/rest/synthesisPurification/removeFile',
            data: $scope.currentFinding
          }).
          then(function (data, status, headers, config) {
            data = data['data']
            $scope.currentFinding = data;
            $scope.addNewFile = false;
            $scope.loader = false;
          }).
          catch(function (data, status, headers, config) {
            data = data['data']
            // called asynchronously if an error occurs
            // or server returns response with an error status.
            // $rootScope.sampleData = data;
            $scope.loader = false;
            $scope.purification.errors = data;
          });
        }
      };



      $scope.getAddNewFile = function () {
        return $scope.addNewFile;
      };

      $scope.closeAddNewFile = function () {
        $scope.addNewFile = false;
      };

      $scope.columnInvalid = function () {
        for (var x = 0; x < $scope.currentFinding.columnHeaders.length; x++) {
          var col = $scope.currentFinding.columnHeaders[x];
          if (!col.columnType || col.columnType == '') {
            return 1
            break
          };
        };
      };

      $scope.disableColumn = function (option) {
        for (var x = 0; x < $scope.currentFinding.columnHeaders.length; x++) {
          var col = $scope.currentFinding.columnHeaders[x];
          if (col.name == option) {
            return 1;
            break;
          };

        };
      };



      $scope.openAddNewFile = function () {
        $scope.addNewFile = true;
        $scope.fileForm = {};
        $scope.fileObject = null;

        $scope.fileFormIndex=-1;
        $scope.fileForm.uriExternal = 'false';
        $scope.externalUrlEnabled = false;
    }


      // end finding section code //
    });

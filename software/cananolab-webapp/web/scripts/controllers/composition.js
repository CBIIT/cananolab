'use strict';
var app = angular.module('angularApp')
  .controller('CompositionCtrl', function (sampleService,navigationService, groupService, $rootScope,$scope,$http,$location,$filter,$routeParams) {
    $rootScope.tabs = navigationService.query();
    $rootScope.groups = groupService.get();
    $scope.sampleData = sampleService.sampleData;
    $scope.sampleId = sampleService.sampleId;

    // Displays left hand nav for samples section. navTree shows nav and navDetail is page index //
    $rootScope.navTree = true;
    $rootScope.navDetail = 1;
    $scope.sampleData = sampleService.sampleData;

     $scope.goBack = function() {
      $location.path("/sampleResults").replace();
      $location.search('sampleId', null);      };
      
      $scope.select = function(tab) {
          var size = 0, key;
          for (key in $scope.compositionSections) {
            size+=1
          };

          for (var x=0; x<size;x++) {
              if (tab>=0) {
                  if (x==tab){
                      $scope['show'+x]=false;                
                  }
                  else {
                      $scope['show'+x]=true;
                  } 
              }    
              else {
                  $scope['show'+x]=false;
              }      
          }
      }; 

	$http({method: 'GET', url: 'http://localhost:8080/caNanoLab/rest/composition/summaryView?sampleId=20917507'}).
            success(function(data, status, headers, config) {
                $scope.compositionSections = data.compositionSections;
                $scope.nanomaterialentity = data.nanomaterialentity;
                $scope.functionalizingentity = data.functionalizingentity;
                $scope.chemicalassociation = data.chemicalassociation;
                $scope.compositionfile = data.compositionfile;
                $scope.sampleId = '20917507';
                $scope.index = 1;
            }).
            error(function(data, status, headers, config) {
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope.message = data;

            });

  });


'use strict';

var app = angular.module('angularApp')
  .controller('EditSynthesisCtrl', function (sampleService, utilsService, navigationService, groupService, $rootScope, $scope, $http, $location, $filter, $modal, $routeParams) {
    $scope.sampleName = '';
    $scope.sampleId = $location.search()['sampleId'];  

    $scope.materials = [];
    $scope.functionalizations = [];
    $scope.purifications = [];
    $scope.showForm = false;
    $scope.openMaterialEditForm = function () {
      if ($scope.showForm == false) {
        $scope.showForm = true;
      }
      else {
        $scope.showForm = false;
      };
    };

    $scope.loader = true;

    $http({ method: 'GET', url: '/caNanoLab/rest/synthesis/summaryView?sampleId=' + $scope.sampleId }).
      success(function (data, status, headers, config) {
        $scope.sampleName = sampleService.sampleName($scope.sampleId);
        $scope.materials = data.synthesisMaterials;
        $scope.functionalizations = data.synthesisFunctionalization;
        $scope.purifications = data.synthesisPurification;
        $scope.loader = false;
      });

    $http({ method: 'GET', url: '/caNanoLab/rest/sample/getCurrentSampleName?sampleId=' + $scope.sampleId }).
      success(function (data, status, headers, config) {
        $scope.sampleName = sampleService.sampleName($scope.sampleId);
        $scope.materials = data.synthesisMaterials;
        $scope.functionalizations = data.synthesisFunctionalization;
        $scope.purifications = data.synthesisPurification;
        $scope.loader = false;
      });      
  });
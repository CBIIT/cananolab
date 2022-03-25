'use strict';
var app = angular.module('angularApp')

    .controller('BatchDataAvailabilityCtrl', function (navigationService, groupService, $rootScope, $scope, $http, $location) {
        $rootScope.tabs = navigationService.get();
        $rootScope.groups = groupService.getGroups.data.get();

        $scope.generateBatchDataAvailabilityForm = {};
        $scope.generateBatchDataAvailabilityForm.option = 'generate all';


        $scope.doSubmit = function () {
            $scope.options = {"option":$scope.generateBatchDataAvailabilityForm.option}
            $scope.loader = true;

            $http({
                method: 'POST',
                url: '/caNanoLab/rest/curation/generateBatchDataAvailability',
                data: $scope.options
            }).
            then(function (data, status, headers, config) {
                data = data['data']
                $scope.loader = false;
                $rootScope.tabs = navigationService.get();
                $scope.messages = data;
            }).
            catch(function (data, status, headers, config) {
                data = data['data']
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                // $rootScope.sampleData = data;
                $scope.loader = false;
                $scope.messages = data;
            });
        };

        $scope.resetForm = function () {
            $scope.generateBatchDataAvailabilityForm = {};
        };
    });
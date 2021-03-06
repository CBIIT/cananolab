'use strict';
var app = angular.module('angularApp')

    .controller('UserSearchCtrl', function (userService, navigationService, groupService, $rootScope, $scope, $http, $location) {
        $scope.searchUserForm = {};
        $scope.userData = userService.userData;
        $rootScope.tabs = navigationService.get();
        $rootScope.groups = groupService.getGroups.data.get();

        $scope.doSearch = function () {
            $scope.loader = true;
            for (var key in $scope.researchArea) {
                if ($scope.researchArea[key]) {
                    $scope.searchPublicationForm.researchArea.push(key)
                }

            }

            $http({
                method: 'GET',
                url: '/caNanoLab/rest/useraccount/search',
                params: $scope.searchUserForm
            }).
            then(function (data, status, headers, config) {
                data = data['data']
                $scope.loader = false;
                $scope.userData.data = data;
                $location.path("/userResults").replace();
            }).
            catch(function (data, status, headers, config) {
                data = data['data']
                // called asynchronously if an error occurs
                // or server returns response with an error status.
                $scope.loader = false;
                $scope.message = data;
            });
        };

        $scope.resetForm = function () {
            $scope.searchUserForm = {};
        };

    });
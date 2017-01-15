'use strict';

angular.module('BSKApplication.Office', ['ngResource'])
    .controller('OfficeController', ['$scope', '$resource', '$routeParams', '$location', 'OfficeFactory',
        function ($scope, $resource, $routeParams, $location, OfficeFactory) {
        $scope.offices = OfficeFactory.query();

        $scope.noAccessError = false;
        $scope.cantDeleteError = false;
        $scope.delete = function (idOffice) {
            OfficeFactory.delete({id: idOffice}).$promise.then(function(data) {
                $scope.noAccessError = false;
                $scope.cantDeleteError = false;
                $scope.offices = OfficeFactory.query();
            }, function(error) {
                if (error.status === 409) {
                    $scope.cantDeleteError = true;
                } else {
                    $scope.noAccessError = true;
                }
            });
        };
    }])
    .controller('OfficeDetailController', ['$scope', '$resource', '$routeParams', '$location', 'OfficeFactory',
        function ($scope, $resource, $routeParams, $location, OfficeFactory) {
            if ($routeParams.id !== '-1'){
                OfficeFactory.get({id: $routeParams.id}, function(office) {
                    $scope.office = office;
                    $scope.newData = angular.extend({}, $scope.office);
                });
            }

            $scope.newData = {};

            $scope.back = function () {
                $scope.noAccessError = false;
                $location.path('/office');
            };

            $scope.noAccessError = false;
            $scope.sameLoginError = false;

            $scope.save = function () {
                if ($routeParams.id === '-1'){
                    $scope.newData.idOffice = null;
                    OfficeFactory.save($scope.newData).$promise.then(function(data) {
                        $scope.noAccessError = false;
                        $scope.back();
                    }, function(error) {
                        if (error.status === 403) {
                            $scope.noAccessError = true;
                        } else {
                            $scope.sameLoginError = true;
                        }
                    });
                } else {
                    OfficeFactory.update({id: $scope.newData.idOffice}, $scope.newData).$promise.then(function(data) {
                        $scope.noAccessError = false;
                        $scope.back();
                    }, function(error) {
                        if (error.status === 403) {
                            $scope.noAccessError = true;
                        } else {
                            $scope.sameLoginError = true;
                        }
                    });
                }
            };

        }])
;

'use strict';

angular.module('BSKApplication.Doctor', ['ngResource'])
    .controller('DoctorController', ['$scope', '$resource', '$routeParams', '$location', 'DoctorFactory',
        function ($scope, $resource, $routeParams, $location, DoctorFactory) {
        $scope.doctors = DoctorFactory.query();

        $scope.noAccessError = false;
        $scope.cantDeleteError = false;

        $scope.delete = function (idDoctor) {
            DoctorFactory.delete({id: idDoctor}).$promise.then(function(data) {
                $scope.noAccessError = false;
                $scope.cantDeleteError = false;
                $scope.doctors = DoctorFactory.query();
            }, function(error) {
                if (error.status === 409) {
                    $scope.cantDeleteError = true;
                } else {
                    $scope.noAccessError = true;
                }
            });
        };
    }])
    .controller('DoctorDetailController', ['$scope', '$resource', '$routeParams', '$location', 'DoctorFactory', '$rootScope',
        function ($scope, $resource, $routeParams, $location, DoctorFactory, $rootScope) {

            if ($routeParams.id !== '-1') {
                DoctorFactory.get({id: $routeParams.id}, function(doctor) {
                    $scope.doctor = doctor;
                    $scope.newData = angular.extend({}, $scope.doctor);
                });
            }

            $scope.newData = {};

            $scope.back = function () {
                $location.path('/doctor');
            };

            $scope.noAccessError = false;
            $scope.sameLoginError = false;
            $scope.save = function () {
                if ($routeParams.id === '-1'){
                    DoctorFactory.save($scope.newData).$promise.then(function(data) {
                        $scope.noAccessError = false;
                        $scope.sameLoginError = false;
                        $scope.back();
                    }, function(error) {
                        if (error.status === 403) {
                            $scope.noAccessError = true;
                        } else {
                            $scope.sameLoginError = true;
                        }
                    });
                } else {
                    DoctorFactory.update({id: $scope.newData.userId}, $scope.newData).$promise.then(function(data) {
                        $scope.noAccessError = false;
                        if ($rootScope.username === $scope.newData.login){
                            $rootScope.authLevel = $scope.newData.label;
                            $rootScope.username = $scope.newData.login;
                        }
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

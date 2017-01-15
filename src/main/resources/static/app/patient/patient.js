'use strict';

angular.module('BSKApplication.Patient', ['ngResource'])
    .controller('PatientController', ['$scope', 'PatientFactory', function ($scope, PatientFactory) {

        $scope.patients = PatientFactory.query();

        $scope.noAccessError = false;
        $scope.cantDeleteError = false;

        $scope.delete = function (idPatient) {
            PatientFactory.delete({id: idPatient}).$promise.then(function(data) {
                $scope.noAccessError = false;
                $scope.cantDeleteError = false;
                $scope.patients = PatientFactory.query();
            }, function(error) {
                if (error.status === 409) {
                    $scope.cantDeleteError = true;
                } else {
                    $scope.noAccessError = true;
                }
            });
        };
    }])
    .controller('PatientDetailController', ['$scope', '$resource', '$routeParams', '$location', 'PatientFactory', '$filter',
        function ($scope, $resource, $routeParams, $location, PatientFactory, $filter) {
            PatientFactory.get({id: $routeParams.id}, function(patient) {
            $scope.patient = patient;
            $scope.patient.birthDay = $filter('date')(new Date($scope.patient.birthDay), 'dd.MM.yyyy');
        });


        $scope.model = {
            IsEditing: false
        };
        $scope.newData = {};
        $scope.back = function () {
            $scope.noAccessError = false;
            $location.path('/patient');
        };


        $scope.noAccessError = false;
        $scope.sameLoginError = false;

        $scope.save = function () {
            $scope.sendingData = {};
            $scope.sendingData.idPatient = $scope.newData.idPatient;
            $scope.sendingData.pesel = $scope.newData.pesel;
            $scope.sendingData.name = $scope.newData.name;
            $scope.sendingData.lastName = $scope.newData.lastName;
            $scope.sendingData.birthDay = $scope.newData.birthDay;
            $scope.sendingData.gender = $scope.newData.gender;
            $scope.sendingData.phone = $scope.newData.phone;

            $scope.sendingData.birthDay = $("#inputBirthday").val();

            PatientFactory.update({id: $scope.sendingData.idPatient}, $scope.sendingData).$promise.then(function(data) {
                $scope.noAccessError = false;
                $scope.back();
            }, function(error) {
                if (error.status === 403) {
                    $scope.noAccessError = true;
                } else {
                    $scope.sameLoginError = true;
                }
            });
        };

        $scope.edit = function () {

            $scope.model.IsEditing = true;
            $scope.newData = angular.extend({}, $scope.patient);
            $(function () {
                $("#inputBirthday").datepicker({
                    format: "dd.mm.yyyy",
                    orientation: "bottom left",
                    language: 'pl',
                    autoclose: true});
            });
        };

        $scope.cancel = function () {
            $scope.model.IsEditing = false;
            $scope.errors = null;
        };

    }])
    .controller('PatientNewController', ['$scope', '$resource', '$routeParams', '$location', 'PatientFactory', '$filter',
        function ($scope, $resource, $routeParams, $location, PatientFactory, $filter) {

            $scope.newData = {};
            $scope.back = function () {
                $location.path('/patient');
            };

            $scope.noAccessError = false;
            $scope.sameLoginError = false;

            $scope.save = function () {
                $scope.toSave = {};
                $scope.toSave = $scope.newData;
                $scope.toSave.birthDay = $("#inputBirthday").val();
                PatientFactory.save($scope.toSave).$promise.then(function(data) {
                    $scope.noAccessError = false;
                    $scope.back();
                }, function(error) {
                    if (error.status === 403) {
                        $scope.noAccessError = true;
                    } else {
                        $scope.sameLoginError = true;
                    }
                });
            };
            $(function () {
                $("#inputBirthday").datepicker({
                    format: "dd.mm.yyyy",
                    orientation: "bottom left",
                    autoclose: true,
                    language: 'pl',
                    forceParse: false});
            });

        }])
;

'use strict';

angular.module('BSKApplication.Appointment', ['ngResource'])
    .controller('AppointmentController', ['$scope', '$resource', '$routeParams', '$location', 'AppointmentFactory', '$rootScope', 'AccessFactory',
        function ($scope, $resource, $routeParams, $location, AppointmentFactory, $rootScope, AccessFactory) {
        $scope.appointments = AppointmentFactory.query();
            AccessFactory.get({name: "appointment"}, function(access) {
            $scope.access = access.hasAccess;
        });

        $scope.noAccessError = false;
        $scope.cantDeleteError = false;

        $scope.delete = function (idAppointment) {
            AppointmentFactory.delete({id: idAppointment}).$promise.then(function(data) {
                $scope.noAccessError = false;
                $scope.cantDeleteError = false;
                $scope.appointments = AppointmentFactory.query();
            }, function(error) {
                if (error.status === 409) {
                    $scope.cantDeleteError = true;
                } else {
                    $scope.noAccessError = true;
                }
            });
        };

    }])
    .controller('AppointmentDetailController', ['$scope', '$resource', '$routeParams', '$location', 'AppointmentFactory', 'PatientFactory', 'OfficeFactory', 'DoctorFactory', '$http', 'AccessFactory', 'DiagnosisFactory', '$filter',
        function ($scope, $resource, $routeParams, $location, AppointmentFactory, PatientFactory, OfficeFactory, DoctorFactory, $http, AccessFactory, DiagnosisFactory, $filter) {

            $scope.myInput = {};
            AppointmentFactory.get({id: $routeParams.id}, function(appointment) {
                $scope.appointment = appointment;
                $scope.myInput.date = $filter('date')(new Date($scope.appointment.date), 'dd.MM.yyyy');
                $scope.myInput.time = $filter('date')(new Date($scope.appointment.date), 'HH:mm');

            });

           $scope.getdiagnoses = function() {
                $http({method: 'GET', url:'/api/diagnosis/appointment/' + $routeParams.id}).then(function mySucces(response) {
                    $scope.diagnoses = response.data;
                });
            };
            $scope.getdiagnoses();
            AccessFactory.get({name: "appointment"}, function(access) {
                $scope.access = access.hasAccess;
            });

            $scope.noAccessDeleteError = false;

            $scope.delete = function (idDiagnosis) {
                DiagnosisFactory.delete({id: idDiagnosis}).$promise.then(function(data) {
                    $scope.noAccessDeleteError = false;
                    $scope.getdiagnoses();
                }, function(error) {
                    $scope.noAccessDeleteError = true;
                });
            };


            $scope.model = {
                IsEditing: false
            };
            $scope.newData = {};

            $scope.back = function () {
                $location.path('/appointment');
            };
            $scope.noAccessError = false;

            $scope.save = function () {
                $scope.toSave = {};
                $scope.toSave.idAppointment = $scope.newData.idAppointment;
                $scope.toSave.idDoctor = $scope.newData.idDoctor;
                $scope.toSave.idOffice = $scope.newData.idOffice;
                $scope.toSave.idPatient = $scope.newData.idPatient;
                $scope.toSave.status = $scope.newData.status;
                $scope.toSave.date =  $scope.myInput.date + ' ' + $("#inputTime").val()+ ':00';

                AppointmentFactory.update({id: $scope.toSave.idAppointment}, $scope.toSave).$promise.then(function(data) {
                    $scope.noAccessError = false;
                    $scope.back();
                }, function(error) {
                    $scope.noAccessError = true;
                });
                console.log($scope.toSave);
            };

            $scope.edit = function () {
                $scope.noAccessError = false;
                $scope.model.IsEditing = true;
                $scope.newData = angular.extend({}, $scope.appointment);

                $scope.patients = PatientFactory.query();
                $scope.offices = OfficeFactory.query();
                $scope.doctors = DoctorFactory.query({isDoctor: true});
                $(function () {
                    $("#inputDate").datepicker({
                        format: "dd.mm.yyyy",
                        orientation: "bottom left",
                        autoclose: true,
                        language: 'pl',
                        forceParse: false});
                });
                $(function () {
                    $("#inputTime").timepicker( {
                            showMeridian: false,

                        }
                    );
                    $scope.myInput.time = $("#inputTime").val();
                });
            };

            $scope.cancel = function () {
                $scope.model.IsEditing = false;
                $scope.errors = null;
            };

        }])
    .controller('AppointmentNewController', ['$scope', '$resource', '$routeParams', '$location', 'AppointmentFactory', 'PatientFactory', 'OfficeFactory', 'DoctorFactory',
        function ($scope, $resource, $routeParams, $location, AppointmentFactory, PatientFactory, OfficeFactory, DoctorFactory) {
            $scope.newData = {};
            $scope.patients = PatientFactory.query();
            $scope.offices = OfficeFactory.query();
            $scope.doctors = DoctorFactory.query({isDoctor: true});

            $scope.open = function() {
                $scope.popup1.opened = true;
            };

            $scope.popup1 = {
                opened: false
            };

            $scope.back = function () {
                $location.path('/appointment');
            };

            $scope.save = function () {
                $scope.newData.idAppointment = null;
                $scope.newData.date = $scope.date + ' ' + $scope.time+ ':00';
                AppointmentFactory.save($scope.newData).$promise.then(function(data) {
                    $scope.noAccessError = false;
                    $scope.back();
                }, function(error) {
                    $scope.noAccessError = true;
                });
            };

            $(function () {
                $("#inputDate").datepicker({
                    format: "dd.mm.yyyy",
                    orientation: "bottom left",
                    autoclose: true,
                    language: 'pl',
                    forceParse: false});
            });

            $(function () {
                $("#inputTime").timepicker( {
                        showMeridian: false,

                }
                   );
                $scope.time = $("#inputTime").val();
            });


        }])
;

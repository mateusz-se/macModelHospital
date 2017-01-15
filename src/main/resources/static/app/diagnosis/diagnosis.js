'use strict';

angular.module('BSKApplication.Diagnosis', ['ngResource'])
    .controller('DiagnosisDetailController', ['$scope', '$resource', '$routeParams', '$location', 'DiagnosisFactory', 'DrugFactory', '$filter',
        function ($scope, $resource, $routeParams, $location, DiagnosisFactory, DrugFactory, $filter) {

            if ($routeParams.idDiagnosis !== '-1') {
                DiagnosisFactory.get({id: $routeParams.idDiagnosis}, function(diagnosis) {
                    $scope.diagnosis = diagnosis;
                    $scope.drugsList = $scope.diagnosis.drugs;
                    $scope.newData = angular.extend({}, $scope.diagnosis);
                });
            }
            else {
                $scope.drugsList = [];
            }
            $scope.drugs = DrugFactory.query();
            $scope.newData = {};
            $scope.drugElement = {};

            $scope.back = function () {
                if ($routeParams.fromList === 'true') {
                    $location.search('fromList', null)
                    $location.path('/diagnosis/');

                } else {
                    $location.path('/appointment/' + $routeParams.idAppointment );
                }
            };

            $scope.add = function () {
                var idx = $filter('drugFilter')($scope.drugElement, $scope.drugsList);
                if (idx === -1 && !$.isEmptyObject($scope.drugElement) ) {
                    $scope.drugsList.push($scope.drugElement);
                }
            };

            $scope.delete = function (index) {
                $scope.drugsList.splice(index, 1);
            };

            $scope.noAccessError = false;


            $scope.save = function () {
                $scope.newData.drugs = $scope.drugsList;
                if ($routeParams.idDiagnosis === '-1'){
                    $scope.newData.idAppointment = $routeParams.idAppointment;
                    DiagnosisFactory.save($scope.newData).$promise.then(function(data) {
                        $scope.noAccessError = false;
                        $scope.back();
                    }, function(error) {
                        $scope.noAccessError = true;
                    });
                } else {
                    DiagnosisFactory.update({id: $scope.newData.idDiagnosis}, $scope.newData).$promise.then(function(data) {
                        $scope.noAccessError = false;
                        $scope.back();
                    }, function(error) {
                        $scope.noAccessError = true;
                    });
                }
            };

        }])
    .controller('DiagnosisController', ['$scope', '$resource', '$routeParams', '$location', 'DiagnosisFactory', 'DrugFactory', '$filter',
        function ($scope, $resource, $routeParams, $location, DiagnosisFactory, DrugFactory, $filter) {

            $scope.diagnoses = DiagnosisFactory.query();
            $scope.noAccessDeleteError = false;
            $scope.noAccessError = false;
            $scope.delete = function (idDiagnosis) {
                DiagnosisFactory.delete({id: idDiagnosis}).$promise.then(function(data) {
                    $scope.noAccessDeleteError = false;
                    $scope.diagnoses = DiagnosisFactory.query();
                }, function(error) {
                    $scope.noAccessDeleteError = true;
                });
            };

            $scope.noAccessError = false;


        }])
;


'use strict';

angular.module('BSKApplication.Drug', ['ngResource'])
    .controller('DrugController', ['$scope', '$resource', '$routeParams', '$location', 'DrugFactory',
        function ($scope, $resource, $routeParams, $location, DrugFactory) {
        $scope.drugs = DrugFactory.query();

        $scope.noAccessError = false;

        $scope.delete = function (idDrug) {
            DrugFactory.delete({id: idDrug}).$promise.then(function(data) {
                $scope.noAccessError = false;
                $scope.drugs = DrugFactory.query();
            }, function(error) {
                $scope.noAccessError = true;
            });
        };
    }])
    .controller('DrugDetailController', ['$scope', '$resource', '$routeParams', '$location', 'DrugFactory',
        function ($scope, $resource, $routeParams, $location, DrugFactory) {
            if ($routeParams.id !== '-1'){
                DrugFactory.get({id: $routeParams.id}, function(drug) {
                    $scope.drug = drug;
                    $scope.newData = angular.extend({}, $scope.drug);
                });
            }

            $scope.newData = {};

            $scope.back = function () {
                $scope.noAccessError = false;
                $location.path('/drugs');
            };

            $scope.noAccessError = false;
            $scope.sameLoginError = false;

            $scope.save = function () {
                if ($routeParams.id === '-1'){
                    $scope.newData.idDrug = null;
                    DrugFactory.save($scope.newData).$promise.then(function(data) {
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
                    DrugFactory.update({id: $scope.newData.idDrug}, $scope.newData).$promise.then(function(data) {
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

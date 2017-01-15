'use strict';

angular.module('BSKApplication.TableLabel', ['ngResource'])
    .controller('TableLabelController', ['$scope', '$resource', '$routeParams', '$location', 'TableLabelFactory',
        function ($scope, $resource, $routeParams, $location, TableLabelFactory) {
        $scope.tables = TableLabelFactory.query();
    }])
    .controller('TableLabelDetailController', ['$scope', '$resource', '$routeParams', '$location', 'TableLabelFactory',
        function ($scope, $resource, $routeParams, $location, TableLabelFactory) {
            TableLabelFactory.get({name: $routeParams.name}, function(table) {
                $scope.table = table;
                $scope.newData = angular.extend({}, $scope.table);
            });

            $scope.newData = {};

            $scope.noAccessError = false;

            $scope.back = function () {
                $location.path('/tables');
            };

            $scope.save = function () {
                TableLabelFactory.update({name: $scope.newData.name}, $scope.newData).$promise.then(function(data) {
                    $scope.back();
                    $scope.noAccessError = false;
                }, function(error) {
                    $scope.noAccessError = true;
                });
            };

        }])
;

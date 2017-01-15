'use strict';

angular.module('BSKApplication.Common', ['ngResource'])
    .directive('sampleMenu', [function () {
        return {
            restrict: 'EA',
            replace: true,
            templateUrl: '/app/common/menu.html'
        }
    }])
    .controller('CommonController', ['$scope', '$resource', '$routeParams', '$location', '$rootScope',
        function ($scope, $resource, $routeParams, $location, $rootScope) {
            $scope.user = $rootScope.username;
            $scope.authLevel = $rootScope.authLevel;

        }])
    .directive('showSource', ['$resource', function ($resource) {

        return {
            templateUrl: '/app/common/source.html',
            restrict: 'EA',
            replace: true,
            scope: {
            },
            link: function (scope, elem, attrs) {
                var res = $resource('/api/source');

                scope.file = res.get({ path : attrs.url });
            }
        }
    }])

;
'use strict';

angular.module('BSKApplication.Login', ['ngResource'])
    .controller('LoginController', ['$scope', '$location', "$rootScope", "$http", function ($scope, $location, $rootScope, $http) {
        var authenticate = function (callback) {
                $http.get('api/user').success(function (data) {
                    if (data.name) {
                        $rootScope.authenticated = true;
                        $rootScope.authLevel = data.authorities[0].authority;
                        $rootScope.username = data.name;
                    } else {
                        $rootScope.authenticated = false;
                        $rootScope.authLevel = 0;
                    }
                    callback && callback();
                }).error(function () {
                    $rootScope.authenticated = false;
                    $rootScope.authLevel = 0;
                    callback && callback();
                });
            }
            authenticate();
            $scope.credentials = {};
            $scope.error = false;
            $scope.login = function () {
                $http.post('login', $.param($scope.credentials), {
                    headers: {
                        "content-type": "application/x-www-form-urlencoded"
                    }
                }).success(function (data) {
                    authenticate(function () {
                        if ($rootScope.authenticated) {
                            $location.path("/");
                            $scope.error = false;
                        } else {
                            //$location.path("/#/login");
                            $scope.error = true;
                        }
                    });
                }).error(function (data) {
                   // $location.path("/#/login");
                    $scope.error = true;
                    $rootScope.authenticated = false;
                    $rootScope.authLevel = 0;
                })
            };
    }])
;

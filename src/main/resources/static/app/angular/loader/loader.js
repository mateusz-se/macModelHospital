'use strict';

angular.module('BSKApplication.Angular.Loader', ['BSKApplication.Common']);

angular.module('BSKApplication.Angular.Loader', ['ngResource'])
    .controller('LoaderController', ['$scope', '$resource', function ($scope, $resource) {
        var clientsDelayed = $resource("api/clients/delay");
        var clientsError = $resource("api/clients/error/:error");

        $scope.reload = function () {
            $scope.dataDelayed = clientsDelayed.query();
            $scope.dataError400 = clientsError.query({ error: 400 });
            $scope.dataError500 = clientsError.query({ error: 500 });
        };

        $scope.reload();
    }])
;
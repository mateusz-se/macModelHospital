'use strict';

angular.module('BSKApplication.Filters', ['ngResource'])
    .filter('dateFilter', function($filter) {
    return function(input)
    {
        if(input == null){ return ""; }
        var _date = $filter('date')(new Date(input), 'dd.MM.yyyy');
        return _date.toUpperCase();
    };

})
    .filter('drugFilter', function(){
        return function(drug, drugs) {
            for (var index in drugs) {
                if (drugs[index].idDrug == drug.idDrug) {
                    return index;
                }
            }
            return -1;
        }
    })
    .filter('timeFilter', function($filter) {
        return function (input) {
            if (input == null) {
                return "";
            }
            var _date = $filter('date')(new Date(input), 'HH:mm');
            return _date.toUpperCase();
        };
    });
;

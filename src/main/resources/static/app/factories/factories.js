'use strict';

angular.module('BSKApplication.Factories', ['ngResource'])
    .factory('PatientFactory', ['$resource', function ($resource) {
        return $resource('api/patients/:id', { id: '@id' }, {
            update: {
                method: 'PUT'
            }
        });
    }])
    .factory('AppointmentFactory', ['$resource', function ($resource) {
        return $resource('api/appointments/:id', { id: '@id' }, {
            update: {
                method: 'PUT'
            }
        });
    }])
    .factory('DoctorFactory', ['$resource', function ($resource) {
        return $resource('api/doctors/:id', { id: '@id' }, {
            update: {
                method: 'PUT'
            }
        });
    }])
    .factory('OfficeFactory', ['$resource', function ($resource) {
        return $resource('api/offices/:id', { id: '@id' }, {
            update: {
                method: 'PUT'
            }
        });
    }])
    .factory('TableLabelFactory', ['$resource', function ($resource) {
        return $resource('api/tables/:name', { name: '@name' }, {
            update: {
                method: 'PUT'
            }
        });
    }])
    .factory('DrugFactory', ['$resource', function ($resource) {
        return $resource('api/drugs/:id', { id: '@id' }, {
            update: {
                method: 'PUT'
            }
        });
    }])
    .factory('DiagnosisFactory', ['$resource', function ($resource) {
        return $resource('/api/diagnosis/:id', { id: '@id' }, {
            update: {
                method: 'PUT'
            }
        });
    }])

    .factory('AccessFactory', ['$resource', function ($resource) {
        return $resource('api/access/:name', { name: '@name' }, {
            update: {
                method: 'PUT'
            }
        });
    }])
;
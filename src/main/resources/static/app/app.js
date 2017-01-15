'use strict';

/*
This is an Angular module, which will represent our application
*/
angular.module('BSKApplication', [
    'ngResource',
    'ngRoute',
    'ui.date',
    'ui.bootstrap',
    'BSKApplication.Angular.Caching',
    'BSKApplication.Common',
    'BSKApplication.Config',
    'BSKApplication.Angular.Data',
    'BSKApplication.Angular.Loader',
    'BSKApplication.Appointment',
    'BSKApplication.Patient',
    'BSKApplication.Login',
    'BSKApplication.Office',
    'BSKApplication.Doctor',
    'BSKApplication.Factories',
    'BSKApplication.Filters',
    'BSKApplication.TableLabel',
    'BSKApplication.Drug',
    'BSKApplication.Diagnosis'
]).
config(['$routeProvider', '$provide', '$httpProvider', function ($routeProvider, $provide, $httpProvider) {

    var onlyLoggedIn = function ($location,$q, $rootScope, $http) {
        var deferred = $q.defer();

        if ($rootScope.authenticated === true) {
            deferred.resolve();
        } else {
            $http.get('api/user').success(function (data) {
                if (data.name) {
                    $rootScope.authenticated = true;
                    $rootScope.authLevel = data.authorities[0].authority;
                    $rootScope.username = data.name;
                    deferred.resolve();
                } else {
                    deferred.reject();
                    $location.url('/login');
                }
             });
        }
        return deferred.promise;
    };

    $routeProvider.when(
    '/home', {
        redirectTo: '/patient',
        resolve:{loggedIn:onlyLoggedIn}
    });

    $routeProvider.when(
        '/angular/caching', {
            templateUrl: '/app/angular/caching/index.html'
    });

    $routeProvider.when(
        '/angular/data/remote', {
            templateUrl: '/app/angular/data/index.html',
            controller: 'DatasController'
    });

    $routeProvider.when(
       '/angular/data/remote/:id', {
           templateUrl: '/app/angular/data/detail.html',
           controller: 'DataController'
    });

    $routeProvider.when(
        '/angular/loader', {
            templateUrl: '/app/angular/loader/index.html',
            controller: 'LoaderController'
        });

    $routeProvider.when(
        '/angular/directives', {
            templateUrl: '/app/angular/directives/index.html',
            controller: 'DirectivesController'
    });

    $routeProvider.when(
        '/appointment', {
            templateUrl: '/app/appointment/appointment.html',
            controller: 'AppointmentController',
            resolve:{loggedIn:onlyLoggedIn}
    });
    $routeProvider.when(
        '/appointment/:id', {
            templateUrl: '/app/appointment/appointmentDetails.html',
            controller: 'AppointmentDetailController',
            resolve:{loggedIn:onlyLoggedIn}
    });
    $routeProvider.when(
        '/appointments/new', {
            templateUrl: '/app/appointment/appointmentNew.html',
            controller: 'AppointmentNewController',
            resolve:{loggedIn:onlyLoggedIn}
        });

    $routeProvider.when(
        '/patient', {
            templateUrl: '/app/patient/patient.html',
            controller: 'PatientController',
            resolve:{loggedIn:onlyLoggedIn}
        });


    $routeProvider.when(
        '/patient/:id', {
            templateUrl: '/app/patient/patientDetail.html',
            controller: 'PatientDetailController',
            resolve:{loggedIn:onlyLoggedIn}
        });
    $routeProvider.when(
        '/patients/new', {
            templateUrl: '/app/patient/patientNew.html',
            controller: 'PatientNewController',
            resolve:{loggedIn:onlyLoggedIn}
        });

    $routeProvider.when(
        '/login', {
            templateUrl: '/app/login/login.html',
            controller: 'LoginController'
        });
    $routeProvider.when(
        '/office', {
            templateUrl: '/app/office/office.html',
            controller: 'OfficeController',
            resolve:{loggedIn:onlyLoggedIn}
        });
    $routeProvider.when(
        '/office/:id', {
            templateUrl: '/app/office/officeDetails.html',
            controller: 'OfficeDetailController',
            resolve:{loggedIn:onlyLoggedIn}
        });

    $routeProvider.when(
        '/doctor', {
            templateUrl: '/app/doctor/doctor.html',
            controller: 'DoctorController',
            resolve:{loggedIn:onlyLoggedIn}
        });
    $routeProvider.when(
        '/doctor/:id', {
            templateUrl: '/app/doctor/doctorDetails.html',
            controller: 'DoctorDetailController',
            resolve:{loggedIn:onlyLoggedIn}
        });

    $routeProvider.when(
        '/tables', {
            templateUrl: '/app/tableLabel/table.html',
            controller: 'TableLabelController',
            resolve:{loggedIn:onlyLoggedIn}
        });
    $routeProvider.when(
        '/tables/:name', {
            templateUrl: '/app/tableLabel/tableDetails.html',
            controller: 'TableLabelDetailController',
            resolve:{loggedIn:onlyLoggedIn}
        });

    $routeProvider.when(
        '/drugs', {
            templateUrl: '/app/drugs/drugs.html',
            controller: 'DrugController',
            resolve:{loggedIn:onlyLoggedIn}
        });
    $routeProvider.when(
        '/drugs/:id', {
            templateUrl: '/app/drugs/drugsDetails.html',
            controller: 'DrugDetailController',
            resolve:{loggedIn:onlyLoggedIn}
        });

    $routeProvider.when(
        '/diagnosis/:idAppointment/:idDiagnosis', {
            templateUrl: '/app/diagnosis/diagnosisDetails.html',
            controller: 'DiagnosisDetailController',
            resolve:{loggedIn:onlyLoggedIn}
        });
    $routeProvider.when(
        '/diagnosis', {
            templateUrl: '/app/diagnosis/diagnosis.html',
            controller: 'DiagnosisController',
            resolve:{loggedIn:onlyLoggedIn}
        });

    $routeProvider.otherwise({
        redirectTo: '/home',
        resolve:{loggedIn:onlyLoggedIn}
    });


    $httpProvider.interceptors.push('SmartCacheInterceptor');
}])
;
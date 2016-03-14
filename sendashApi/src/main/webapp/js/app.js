(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                controller: 'HomeController',
                templateUrl: 'home.view.html',
                controllerAs: 'vm'
            })
            .when('/confirm/:uuid', {
                controller: 'RegisterConfirmationController',
                templateUrl: 'confirmRegister.view.html',
                controllerAs: 'vm'
            })
            
            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'login.view.html',
                controllerAs: 'vm'
            })

            .when('/register', {
                controller: 'RegisterController',
                templateUrl: 'register.view.html',
                controllerAs: 'vm'
            })
            .when('/registerPassword', {
                controller: 'RegisterPasswordController',
                templateUrl: 'registerPassword.view.html',
                controllerAs: 'vm'
            })
           
            

            .otherwise({ redirectTo: '/login' });
    }

    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
    function run($rootScope, $location, $cookieStore, $http) {
        // keep user logged in after page refresh
    	const sendash = 'https://sendash.com/';
    	//const sendash = 'http://localhost:8181/sendash/';
        $rootScope.globals = $cookieStore.get('globals') || {sendashBaseURI: sendash};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
        	
        	var path = $location.path().split("/")[1]; //don't worry about path names after the top level.
            var restrictedPage = $.inArray(path, ['login', 'register', 'confirm']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });
    }

})();
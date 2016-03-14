(function () {
    'use strict';

    angular
        .module('app')
        .factory('UserService', UserService);

    UserService.$inject = ['$http', '$rootScope'];
    function UserService($http, $rootScope) {
        var service = {};
        service.Create = Create;
        service.Confirm = Confirm;

        return service;

        function Create(user) {
            return $http.post($rootScope.globals.sendashBaseURI + 'api/register', user).then(handleSuccess, handleError('Error creating user'));
        }
        
        function Confirm(uuid) {
            return $http.post($rootScope.globals.sendashBaseURI + 'api/register/user/' + uuid).then(handleSuccess, handleError('Error confirming user'));
        }

        // private functions
        function handleSuccess(res) {
        	res.success = true;
            return res;
        }

        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
    }

})();

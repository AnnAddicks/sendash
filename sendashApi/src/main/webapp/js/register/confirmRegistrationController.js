(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterConfirmationController', RegisterController);

    RegisterController.$inject = ['UserService', '$routeParams', '$location', '$rootScope', 'FlashService'];
    function RegisterController(UserService, $routeParams, $location, $rootScope, FlashService) {
        var vm = this;
        var uuid = $routeParams.uuid;

        vm.confirm = confirm;

        function confirm() {
        	UserService.Create(uuid)
            .then(function (response) {
                if (response.success) {
                	//TODO need to test if we need a password
                    FlashService.Success('Email Confirmed', true);
                    $location.path('/login');
                } else {
                    FlashService.Error(response.message);
                    vm.dataLoading = false;
                }
            });
        }
    }

})();

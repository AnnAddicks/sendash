(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterConfirmationController', RegisterConfirmationController);

    RegisterConfirmationController.$inject = ['UserService', '$routeParams', '$location', '$rootScope', 'FlashService'];
    function RegisterConfirmationController(UserService, $routeParams, $location, $rootScope, FlashService) {
        var vm = this;
        var uuid = $routeParams.uuid;

        vm.confirm = confirm;
        console.log("HEY!  INSIDE RegisterConfirmationController!!");
        function confirm() {
        	UserService.Confirm(uuid)
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

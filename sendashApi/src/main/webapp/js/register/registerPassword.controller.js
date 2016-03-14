(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterPasswordController', RegisterController);

    RegisterPasswordController.$inject = ['UserService', '$location', '$rootScope', 'FlashService'];
    function RegisterPasswordController(UserService, $location, $rootScope, FlashService) {
        var vm = this;

        vm.registerPassword = registerPassword;

        function registerPassword() {
            vm.dataLoading = true;
            UserService.RegisterPassword(vm.user)
                .then(function (response) {
                    if (response.success) {
                        FlashService.Success('Registration successful', true);
                        $location.path('/login');
                    } else {
                        FlashService.Error(response.message);
                        vm.dataLoading = false;
                    }
                });
        }
    }

})();

(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterConfirmationController', RegisterConfirmationController);

    RegisterConfirmationController.$inject = ['UserService', '$routeParams', '$location', '$rootScope', 'FlashService'];
    function RegisterConfirmationController(UserService, $routeParams, $location, $rootScope, FlashService) {
        var vm = this;
        var uuid = $routeParams.uuid;
        
    	UserService.Confirm(uuid)
        .then(function (response) {
            if (response.success) {
            	FlashService.Success('Email Confirmed', true);
            	
            	if(response.needsPassword) {
            		 $location.path('/registerPassword');
            	}
            	else {
            		 $location.path('/login');
            	}
                
               
            } else {
                FlashService.Error(response.message);
                vm.dataLoading = false;
            }
        });
        
    }

})();

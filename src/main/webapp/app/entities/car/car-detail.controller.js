(function() {
    'use strict';

    angular
        .module('cartracker3App')
        .controller('CarDetailController', CarDetailController);

    CarDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Car', 'Maintrec'];

    function CarDetailController($scope, $rootScope, $stateParams, previousState, entity, Car, Maintrec) {
        var vm = this;

        vm.car = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cartracker3App:carUpdate', function(event, result) {
            vm.car = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

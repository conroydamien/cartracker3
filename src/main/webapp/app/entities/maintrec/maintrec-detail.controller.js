(function() {
    'use strict';

    angular
        .module('cartracker3App')
        .controller('MaintrecDetailController', MaintrecDetailController);

    MaintrecDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Maintrec', 'Car'];

    function MaintrecDetailController($scope, $rootScope, $stateParams, previousState, entity, Maintrec, Car) {
        var vm = this;

        vm.maintrec = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cartracker3App:maintrecUpdate', function(event, result) {
            vm.maintrec = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

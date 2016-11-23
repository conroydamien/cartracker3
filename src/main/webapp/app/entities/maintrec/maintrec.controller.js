(function() {
    'use strict';

    angular
        .module('cartracker3App')
        .controller('MaintrecController', MaintrecController);

    MaintrecController.$inject = ['$scope', '$state', 'Maintrec'];

    function MaintrecController ($scope, $state, Maintrec) {
        var vm = this;
        
        vm.maintrecs = [];

        loadAll();

        function loadAll() {
            Maintrec.query(function(result) {
                vm.maintrecs = result;
            });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('cartracker3App')
        .controller('MaintrecDeleteController',MaintrecDeleteController);

    MaintrecDeleteController.$inject = ['$uibModalInstance', 'entity', 'Maintrec'];

    function MaintrecDeleteController($uibModalInstance, entity, Maintrec) {
        var vm = this;

        vm.maintrec = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Maintrec.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

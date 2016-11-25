(function() {
    'use strict';

    angular
        .module('cartracker3App')
        .controller('CarDialogController', CarDialogController);

    CarDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Car', 'Maintrec'];

    function CarDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Car, Maintrec) {
        var vm = this;

        vm.car = entity;
        vm.clear = clear;
        vm.save = save;
        vm.getContractID = getContractID;
        vm.maintrecs = Maintrec.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });
        
        function getContractID() {
        	var web3 = new Web3(new Web3.providers.HttpProvider("http://testrpc:8545")); 
            alert(web3.eth.defaultBlock);
            
            var _milage = 4;/* var of type uint256 here */
            var maintenancerecordContract = web3.eth.contract([{"constant":false,"inputs":[{"name":"_milage","type":"uint256"}],"name":"setMilage","outputs":[],"payable":false,"type":"function"},{"constant":true,"inputs":[],"name":"milage","outputs":[{"name":"","type":"uint256"}],"payable":false,"type":"function"},{"inputs":[{"name":"_milage","type":"uint256"}],"type":"constructor"}]);
            var maintenancerecord = maintenancerecordContract.new(
               _milage,
               {
                 from: web3.eth.accounts[0], 
                 data: '606060405260405160208060de833981016040528080519060200190919050505b806000600050819055505b5060a68060386000396000f360606040526000357c010000000000000000000000000000000000000000000000000000000090048063bd9cffe2146043578063ea92c97114605d57603f565b6002565b34600257605b60048080359060200190919050506082565b005b34600257606c6004805050609d565b6040518082815260200191505060405180910390f35b600060005054811115609957806000600050819055505b5b50565b6000600050548156', 
                 gas: 4700000
               }, function (e, contract){
                console.log(e, contract);
                if (typeof contract.address !== 'undefined') {
                     console.log('Contract mined! address: ' + contract.address + ' transactionHash: ' + contract.transactionHash);
                     vm.car.contractID = contract.address;
                }
             })
   
        	alert("getting contract id");
        	console.log("getting contract id");
        }

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.car.id !== null) {
                Car.update(vm.car, onSaveSuccess, onSaveError);
            } else {
                Car.save(vm.car, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cartracker3App:carUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

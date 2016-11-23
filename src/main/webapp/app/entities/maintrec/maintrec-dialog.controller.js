(function() {
    'use strict';

    angular
        .module('cartracker3App')
        .controller('MaintrecDialogController', MaintrecDialogController);

    MaintrecDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Maintrec', 'Car'];

    function MaintrecDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Maintrec, Car) {
        var vm = this;

        vm.maintrec = entity;
        vm.clear = clear;
        vm.save = save;
        vm.cars = Car.query();

        function setMilage(contract, milage) {
            contract.setMilage(milage, {from:web3.eth.accounts[0]});
        }
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
        	var web3 = new Web3(new Web3.providers.HttpProvider("http://localhost:8545"));
        	
        	var myAddress = web3.eth.accounts[0];
        	var myContractID = vm.maintrec.car.contractID;
        	
        	//alert(JSON.stringify(myContractID, null, 4));
        	
        	var maintenancerecordContract = web3.eth.contract([
        	  {"constant":false,"inputs":
        		   [{"name":"_milage","type":"uint256"}],"name":"setMilage",
        		    "outputs":[],"payable":false,"type":"function"},
        		    {"constant":true,"inputs":[],"name":"milage",
        		    "outputs":[{"name":"","type":"uint256"}],
        		    "payable":false,"type":"function"},
        		    {"inputs":[{"name":"_milage","type":"uint256"}],
        		     "type":"constructor"}]);
        	
        	var contractInstance = maintenancerecordContract.at(myContractID);
        	
        	contractInstance.setMilage(vm.maintrec.milage, {from:web3.eth.accounts[0]});
        	
        	contractInstance.milage(function(e,r) 
        			{var newlyEnteredMilage = vm.maintrec.milage;
        		
        		     console.log("blockchain milage:" + r.toNumber());
        			 console.log("newly entered:" + newlyEnteredMilage)
        			 
        			  var blockchainMilage = r.toNumber();
                     console.log("Save? :");
        			 console.log(Number(newlyEnteredMilage) >= Number(blockchainMilage))
        			  
        			  if(newlyEnteredMilage >= blockchainMilage) 
        			  {
      		            vm.isSaving = true;
      		            if (vm.maintrec.id !== null) {
      		                Maintrec.update(vm.maintrec, onSaveSuccess, onSaveError);
      		            } else {
      		                Maintrec.save(vm.maintrec, onSaveSuccess, onSaveError);
      		            }
        		      } else {
        		    	alert("A lower mileage than that in the blockchain (" +
        		    			blockchainMilage + ") cannot be saved. " +
        		    			"Please enter a valid mileage or choose Cancel.")  
        		      }
        			});
        	
        	//setMilage (myContract, 30000);
        	
        }

        function onSaveSuccess (result) {
            $scope.$emit('cartracker3App:maintrecUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

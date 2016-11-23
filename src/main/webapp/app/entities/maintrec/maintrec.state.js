(function() {
    'use strict';

    angular
        .module('cartracker3App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('maintrec', {
            parent: 'entity',
            url: '/maintrec',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cartracker3App.maintrec.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/maintrec/maintrecs.html',
                    controller: 'MaintrecController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('maintrec');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('maintrec-detail', {
            parent: 'entity',
            url: '/maintrec/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cartracker3App.maintrec.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/maintrec/maintrec-detail.html',
                    controller: 'MaintrecDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('maintrec');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Maintrec', function($stateParams, Maintrec) {
                    return Maintrec.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'maintrec',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('maintrec-detail.edit', {
            parent: 'maintrec-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/maintrec/maintrec-dialog.html',
                    controller: 'MaintrecDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Maintrec', function(Maintrec) {
                            return Maintrec.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('maintrec.new', {
            parent: 'maintrec',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/maintrec/maintrec-dialog.html',
                    controller: 'MaintrecDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                milage: null,
                                notes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('maintrec', null, { reload: 'maintrec' });
                }, function() {
                    $state.go('maintrec');
                });
            }]
        })
        .state('maintrec.edit', {
            parent: 'maintrec',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/maintrec/maintrec-dialog.html',
                    controller: 'MaintrecDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Maintrec', function(Maintrec) {
                            return Maintrec.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('maintrec', null, { reload: 'maintrec' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('maintrec.delete', {
            parent: 'maintrec',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/maintrec/maintrec-delete-dialog.html',
                    controller: 'MaintrecDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Maintrec', function(Maintrec) {
                            return Maintrec.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('maintrec', null, { reload: 'maintrec' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

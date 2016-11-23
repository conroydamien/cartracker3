(function() {
    'use strict';
    angular
        .module('cartracker3App')
        .factory('Maintrec', Maintrec);

    Maintrec.$inject = ['$resource'];

    function Maintrec ($resource) {
        var resourceUrl =  'api/maintrecs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

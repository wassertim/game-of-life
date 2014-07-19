angular.module('gameOfLife').service('GenerationHttp', function ($http) {
    return {
        init: function (config) {
            return $http.get('/universe/new/' + config.rowsCount + '/' + config.columnsCount).then(function (response) {
                return response.data;
            });
        },
        next: function (data) {
            return $http({
                url:'/universe/next-generation',
                method: 'POST',
                'Content-Type': 'application/json',
                data: data
            }).then(function (response) {
                return response.data;
            });
        }
    };
});

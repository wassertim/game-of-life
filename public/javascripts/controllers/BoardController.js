angular.module('gameOfLife').controller('BoardController',
    ['$scope', '$injector', function ($scope, $injector) {
        var generation = $injector.get('Generation');
        $scope.codeVersion = {
            isServer: false
        };
        $scope.config = {
            rowsCount: 15,
            columnsCount: 45
        };
        $scope.toggleVersion = function () {
            $scope.codeVersion.isServer = !$scope.codeVersion.isServer;
            generation = $scope.codeVersion.isServer ? $injector.get('GenerationHttp') : $injector.get('Generation');
            $scope.newUniverse();
        };
        $scope.newUniverse = function () {
            generation.init($scope.config).then(function (data) {
                $scope.generation = data;
            });
        };
        $scope.next = function () {
            generation.next($scope.generation).then(function (data) {
                $scope.generation = data;
            });
        };
        $scope.toggle = function (row, cell) {
            $scope.generation[row].cells[cell].isAlive = !$scope.generation[row].cells[cell].isAlive;
        };
        $scope.newUniverse();

    }]);

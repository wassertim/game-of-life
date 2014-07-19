angular.module('gameOfLife').service('Generation', function ($q, $timeout) {
    //var deferred = $q.defer();
    return {
        init: function (config) {
            return $timeout(function () {
                var rows = config.rowsCount,
                    cols = config.columnsCount;
                var generation = [];
                for (var x = 0; x < rows; x++) {
                    var row = {cells: []};
                    for (var y = 0; y < cols; y++) {
                        row.cells.push({isAlive: false, coordinates: {
                            x: x,
                            y: y
                        }});
                    }
                    generation.push(row);
                }
                return generation;
            });
        },
        next: function (currentGeneration) {
            var that = this;
            return $timeout(function () {
                var nextGeneration = [];
                for (var row = 0; row < currentGeneration.length; row++) {
                    var newRow = {cells: []};
                    for (var col = 0; col < currentGeneration[row].cells.length; col++) {
                        newRow.cells.push(
                            {
                                isAlive: that.isGonnaLive(currentGeneration, row, col) || that.newCell(currentGeneration, row, col),
                                coordinates: {
                                    x: row,
                                    y: col
                                }
                            });
                    }
                    nextGeneration.push(newRow);
                }
                return nextGeneration;
            });
        },
        isGonnaLive: function (board, row, col) {
            return this.isAlive(board, row, col)
                && this.neighboursCount(board, row, col) >= 2
                && this.neighboursCount(board, row, col) <= 3;
        },
        newCell: function (board, row, cell) {
            return !this.isAlive(board, row, cell)
                && this.neighboursCount(board, row, cell) == 3;
        },
        neighboursCount: function (board, row, col) {
            return (this.isAlive(board, row - 1, col - 1) ? 1 : 0) +
                (this.isAlive(board, row - 1, col + 0) ? 1 : 0) +
                (this.isAlive(board, row - 1, col + 1) ? 1 : 0) +
                (this.isAlive(board, row + 0, col - 1) ? 1 : 0) +
                (this.isAlive(board, row + 0, col + 1) ? 1 : 0) +
                (this.isAlive(board, row + 1, col - 1) ? 1 : 0) +
                (this.isAlive(board, row + 1, col + 0) ? 1 : 0) +
                (this.isAlive(board, row + 1, col + 1) ? 1 : 0);
        },
        isAlive: function (generation, row, col) {
            return (row >= 0 && row < generation.length &&
                col >= 0 && col < generation[row].cells.length &&
                generation[row].cells[col].isAlive);
        }
    };
});

package service

import model.{Coord, Cell, Row}

class GenerationService(rows: Int, cols: Int) {
  private val cachedGeneration: Seq[Row] = init(rows, cols)
  private def init(rowsCount: Int, columnsCount: Int): Seq[Row] = {
    (0 to rowsCount-1).map(row => Row((0 to columnsCount-1).map(col => Cell(isAlive = false, Coord(row, col)))))
  }

  def current = cachedGeneration
  def next(currentGeneration: Seq[Row]): Seq[Row] = {
    currentGeneration.map(r => Row(r.cells.map(cell =>
      Cell(isGonnaLive(cell, currentGeneration) || newLife(currentGeneration, cell), cell.coordinates)))
    )
  }

  private def isAlive(rows: Seq[Row], c: Coord): Boolean = {
    c.x >= 0 && c.x < rows.size && c.y >= 0 && c.y < rows(c.x).cells.length && rows(c.x).cells(c.y).isAlive
  }

  private def neighboursCount(cell: Cell, rows: Seq[Row]): Int = {
    (if (isAlive(rows, Coord(cell.coordinates.x - 1, cell.coordinates.y - 1))) 1 else 0) +
    (if (isAlive(rows, Coord(cell.coordinates.x - 1, cell.coordinates.y))) 1 else 0) +
    (if (isAlive(rows, Coord(cell.coordinates.x - 1, cell.coordinates.y + 1))) 1 else 0) +
    (if (isAlive(rows, Coord(cell.coordinates.x, cell.coordinates.y - 1))) 1 else 0) +
    (if (isAlive(rows, Coord(cell.coordinates.x, cell.coordinates.y + 1))) 1 else 0) +
    (if (isAlive(rows, Coord(cell.coordinates.x + 1, cell.coordinates.y - 1))) 1 else 0) +
    (if (isAlive(rows, Coord(cell.coordinates.x + 1, cell.coordinates.y))) 1 else 0) +
    (if (isAlive(rows, Coord(cell.coordinates.x + 1, cell.coordinates.y + 1))) 1 else 0)
  }

  private def isGonnaLive(cell: Cell, currentGeneration: Seq[Row]): Boolean =
    cell.isAlive && neighboursCount(cell, currentGeneration) >= 2 && neighboursCount(cell, currentGeneration) <= 3


  private def newLife(currentGeneration: Seq[Row], cell: Cell): Boolean =
    !isAlive(currentGeneration, cell.coordinates) && neighboursCount(cell, currentGeneration) == 3
}

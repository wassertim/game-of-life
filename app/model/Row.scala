package model

import play.api.libs.functional.syntax._
import play.api.libs.json._
import model.Cell.writes

case class Row(cells: Seq[Cell])
object Row {
  implicit val writes: Writes[Row] = new Writes[Row] {
    def writes(row: Row) = Json.obj(
      "cells" -> row.cells
    )
  }
  implicit val reads: Reads[Row] = (__ \ 'cells).read[Seq[Cell]].map{ l => Row(l) }
}



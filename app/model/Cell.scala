package model

import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, JsPath, Writes}
import model.Coord.writes
case class Cell(isAlive: Boolean, coordinates: Coord)
object Cell {
  implicit val writes: Writes[Cell] = (
    (JsPath \ "isAlive").write[Boolean] and
    (JsPath \ "coordinates").write[Coord]
  )(unlift(Cell.unapply))
  implicit val reads: Reads[Cell] = (
    (JsPath \ "isAlive").read[Boolean] and
      (JsPath \ "coordinates").read[Coord]
    )(Cell.apply _)
}

package model

import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, JsPath, Writes}

case class Coord(x: Int, y: Int)
object Coord {
  implicit val writes: Writes[Coord] = (
    (JsPath \ "x").write[Int] and
    (JsPath \ "y").write[Int]
    )(unlift(Coord.unapply))
  implicit val reads: Reads[Coord] = (
    (JsPath \ "x").read[Int] and
      (JsPath \ "y").read[Int]
    )(Coord.apply _)
}

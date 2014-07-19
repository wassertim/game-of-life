package controllers

import model.Row
import play.api.cache.Cache
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import service.GenerationService
import play.api.Play.current
import model.Row.writes

object Generation extends Controller {
  def initUniverse(rows: Int, cols: Int) = Action {
    val generationService = new GenerationService(rows, cols)
    Cache.set("generation", generationService)
    Ok(Json.toJson(generationService.current))
  }

  def nextGeneration = Action(parse.json) {
    request =>
      val generationService = Cache.getAs[GenerationService]("generation")
      val currentGeneration = request.body.as[Seq[Row]]

      generationService match {
        case Some(g) => {
          val nextGen = g.next(currentGeneration)
          Ok(Json.toJson(nextGen))
        }
        case _ => BadRequest
      }

  }
}

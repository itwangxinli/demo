package controllers

import akka.actor.ActorSystem
import javax.inject.Inject
import org.pac4j.core.context.Pac4jConstants
import org.pac4j.core.context.session.SessionStore
import org.pac4j.core.profile._
import org.pac4j.http.client.indirect.FormClient
import org.pac4j.play.PlayWebContext
import org.pac4j.play.scala._
import play.api.libs.json.Json
import play.api.mvc._

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext

class Application @Inject()(cc: DefaultSecurityComponents, actorSystem: ActorSystem)(implicit exec: ExecutionContext) extends AbstractController(cc) {

  private def getProfiles(implicit request: RequestHeader): List[CommonProfile] = {
    val webContext = new PlayWebContext(request,cc.playSessionStore)
    val profileManager = new ProfileManager[CommonProfile](webContext)
    val profiles = profileManager.getAll(true)
    asScalaBuffer(profiles).toList
  }

  def index = Action { implicit request =>
    val webContext = new PlayWebContext(request, cc.playSessionStore)
    val sessionStore = webContext.getSessionStore().asInstanceOf[SessionStore[PlayWebContext]]
    val sessionId = sessionStore.getOrCreateSessionId(webContext)
    val csrfToken = sessionStore.get(webContext, Pac4jConstants.CSRF_TOKEN).asInstanceOf[String]
    Ok(views.html.demo(getProfiles, csrfToken, sessionId))
  }



  def index2 = Action { implicit request: Request[AnyContent] =>
    val webContext = new PlayWebContext(request, cc.playSessionStore)
    val profileManager = new ProfileManager[CommonProfile](webContext)
    val profiles = profileManager.getAll(true)
    asScalaBuffer(profiles).toList
    Ok(views.html.index("Your new application is ready!"))
  }


  def protectedCustomIndex = Action { implicit request =>
    Ok(views.html.protectedIndex(getProfiles))
  }

  def formIndex = Action { implicit request =>
    Ok(views.html.protectedIndex(getProfiles))
  }

  // Setting the isAjax parameter is no longer necessary as AJAX requests are automatically detected:
  // a 401 error response will be returned instead of a redirection to the login url.
  def formIndexJson = Action { implicit request =>
    val content = views.html.protectedIndex.render(getProfiles)
    val json = Json.obj("content" -> content.toString())
    Ok(json).as("application/json")
  }





  def loginForm = Action { request =>
    val formClient =cc. config.getClients.findClient("FormClient").asInstanceOf[FormClient]
    Ok(views.html.loginForm.render(formClient.getCallbackUrl))
  }


}

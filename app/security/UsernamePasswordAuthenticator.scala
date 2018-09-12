package security

import model.User
import org.pac4j.core.context.{Pac4jConstants, WebContext}
import org.pac4j.core.credentials.UsernamePasswordCredentials
import org.pac4j.core.credentials.authenticator.Authenticator
import org.pac4j.core.exception.{AccountNotFoundException, BadCredentialsException, CredentialsException}
import org.pac4j.core.profile.CommonProfile
import org.pac4j.core.util.CommonHelper
import scala.collection.JavaConverters._

class UsernamePasswordAuthenticator extends Authenticator[UsernamePasswordCredentials] {
  override def validate(credentials: UsernamePasswordCredentials, context: WebContext): Unit = {
    if (credentials == null) {
      throw new CredentialsException("No credential")
    }

    val username = credentials.getUsername

    val password = credentials.getPassword

    if (CommonHelper.isBlank(username)) {
      throw new CredentialsException("Username cannot be blank")
    }

    if (CommonHelper.isBlank(password)) {
      throw new CredentialsException("Password cannot be blank")
    }

    val matchedUser = User.find.query().where()
      .eq("userName", username).findOne();

    if (matchedUser == null) {
      throw new AccountNotFoundException("Username : '" + username + "' does not match password")
    } else if (matchedUser.getPassword != password) {
      throw new BadCredentialsException("Your password is not correct!")
    }

    val profile = new CommonProfile
    profile.setId(username)
    profile.addAttribute(Pac4jConstants.USERNAME, username)
    credentials.setUserProfile(profile)
    profile.setPermissions(Set("read", "write").asJava)
    profile.setRoles(Set("admin", "finance").asJava)
  }
}

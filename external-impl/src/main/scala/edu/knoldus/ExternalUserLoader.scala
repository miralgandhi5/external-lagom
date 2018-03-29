package edu.knoldus

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import com.lightbend.lagom.scaladsl.server.{LagomApplication, LagomApplicationContext, LagomApplicationLoader, LagomServer}
import com.softwaremill.macwire.wire
import play.api.libs.ws.ahc.AhcWSComponents

class ExternalUserLoader extends LagomApplicationLoader {
  override def load(context: LagomApplicationContext): LagomApplication =
    new ExternalUserApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new ExternalUserApplication(context) with LagomDevModeComponents {

    }

  override def describeService = Some(readDescriptor[ExternalUserService])
}

abstract class ExternalUserApplication(context: LagomApplicationContext)
  extends LagomApplication(context) with AhcWSComponents {

  // Bind the service that this server provides

  override lazy val lagomServer: LagomServer = serverFor[ExternalUserService](wire[ExternalUserServiceImpl])
  lazy val externalService = serviceClient.implement[UserService]

}




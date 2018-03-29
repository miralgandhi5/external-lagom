package edu.knoldus

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.transport.Method.{DELETE, POST, PUT}
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import edu.knoldus.entities.{User, UserData}

trait ExternalUserService extends Service {

  override final def descriptor: Descriptor = {
    import Service._
    // @formatter:off
    named("external-lagom")
      .withCalls(
        pathCall("/api/external/user/:id",externalGetUser _),
        restCall(POST, "/api/external/user/addUser",externalAddUser ),
        restCall(PUT, "/api/external/user/updateUser/:id", externalUpdateUser _),
        restCall(DELETE, "/api/external/user/deleteUser/:id", externalDeleteUser _)
      )
  }.withAutoAcl(true)

  def externalAddUser: ServiceCall[User, Done]
  def externalGetUser(id: Int): ServiceCall[NotUsed, User]
  def externalDeleteUser(id: Int): ServiceCall[NotUsed, String]
  def externalUpdateUser(id: Int): ServiceCall[UserData, String]
}

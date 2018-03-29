package edu.knoldus

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.ServiceCall
import edu.knoldus.entities.{User, UserData}

import scala.concurrent.ExecutionContext


class ExternalUserServiceImpl(userService: UserService)(implicit ec: ExecutionContext) extends ExternalUserService {

  /**
    * says hello to given id using external hello service.
    *
    * @param id name to greet.
    * @return string is returned.
    */
  def getExternalHello(id: String): ServiceCall[NotUsed, String] = ServiceCall { _ =>
    userService.hello(id).invoke()
  }

  /**
    * adds User to userList using external service.
    *
    * @return done.
    */
  override def externalAddUser: ServiceCall[User, Done] = ServiceCall { user =>
    userService.addUser().invoke(user)
  }

  /**
    * gets user of given id using external service.
    *
    * @param id id of user.
    * @return user with given id if found else dummy user.
    */

  override def externalGetUser(id: Int): ServiceCall[NotUsed, User] = { _ =>
    userService.getUser(id).invoke()
  }

  /**
    * deletes user of given id using external service.
    *
    * @param id id of user.
    * @return list of existing user.
    */

  override def externalDeleteUser(id: Int): ServiceCall[NotUsed, String] = { _ =>
    userService.deleteUser(id).invoke()
  }

  /**
    * updates user of given id using external service.
    *
    * @param id id of user.
    * @return all users with update.
    */

  override def externalUpdateUser(id: Int): ServiceCall[UserData, String] = { userData =>
    userService.updateUser(id).invoke(userData)
  }
}


name := "external-lagom"

version := "0.1"

scalaVersion := "2.12.5"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lagomServiceLocatorPort in ThisBuild := 10000
lagomServiceGatewayPort in ThisBuild := 9010

lazy val `external-api` = (project in file("external-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `external-impl` = (project in file("external-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      macwire,
      scalaTest,
      "edu.knoldus" %% "user-api" % "1.0-SNAPSHOT"
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`external-api`)

lazy val user = lagomExternalScaladslProject("user-lagom","edu.knoldus" %% "user-impl" % "1.0-SNAPSHOT")

// The Play plugin
addSbtPlugin("org.playframework" % "sbt-plugin" % "3.0.0")

// Defines scaffolding (found under .g8 folder)
// http://www.foundweekends.org/giter8/scaffolding.html
// sbt "g8Scaffold form"

//Make sure you have the correct version for your play application
//This is Play 3
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "0.16.2")
addSbtPlugin("org.playframework" % "sbt-play-ebean" % "8.0.0-M1")

addSbtPlugin("com.github.sbt" % "sbt-eclipse" % "6.0.0")
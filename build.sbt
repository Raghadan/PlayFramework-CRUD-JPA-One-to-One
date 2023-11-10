



lazy val root = (project in file("."))
				.enablePlugins(PlayJava,PlayEbean)
				.settings(
				name := "play-poc-2-jpa",
				organization := "com.example",
				version := "1.0-SNAPSHOT",				
				scalaVersion := "2.13.12",

				libraryDependencies ++= Seq(
  								javaJpa,guice,
								"org.postgresql" % "postgresql" % "42.6.0",
	  							javaJdbc,
	  							jdbc
	  							),
				EclipseKeys.preTasks := Seq(compile in Compile),
				EclipseKeys.projectFlavor := EclipseProjectFlavor.Java,          
				EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources) 
)





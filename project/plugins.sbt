addSbtPlugin("org.scalameta"     % "sbt-scalafmt" % "2.5.0")
addSbtPlugin("com.github.sbt"    % "sbt-git"      % "2.0.1")
addSbtPlugin("de.heikoseeberger" % "sbt-header"   % "5.9.0")
addSbtPlugin("com.typesafe.play" % "sbt-twirl"    % "1.6.0-M7")
addSbtPlugin("com.timushev.sbt"  % "sbt-updates"  % "0.6.3")

/**
  * The old Scala XML is pulled from Scala 2.12.x.
  *
  * [error] (update) found version conflict(s) in library dependencies; some are suspected to be binary incompatible:
  * [error]
  * [error] 	* org.scala-lang.modules:scala-xml_2.12:2.1.0 (early-semver) is selected over 1.0.6
  * [error] 	    +- org.scoverage:scalac-scoverage-reporter_2.12:2.0.7 (depends on 2.1.0)
  * [error] 	    +- org.scala-lang:scala-compiler:2.12.16              (depends on 1.0.6)
  */
libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always

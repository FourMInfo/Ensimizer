//@doc("Ensimizer allows you to ensime-ize an Ammonite script project directory so you can edit the scripts as Scala code in many text editor")
// TO DO parameterize the vals at top
// TO DO create .gitignore file so can check-in scripts w/o all the sbt/ensime overheads and link files
// TO DO 

val wd = pwd

@main
def ensimize(target:String) = {
  //create build.sbt
  val projname = wd.last
  val sversion = "2.12.5"
  val sbtversion = "1.1.5"
  val sbtEnsimeVersion = "2.6.0"
  write(wd/"build.sbt",s"""lazy val root = (project in file("."))
                    .settings(
                      name := \"$projname\",
                      scalaVersion := \"$sversion\",
                      ensimeScalaVersion in ThisBuild := \"$sversion\"
                    )
                    """)
  // create project/build.properties
  write(wd/'project/"build.properties",s"sbt.version=$sbtversion\n")
  // create plugins.sbt
  write(wd/'project/"plugins.sbt",s"""addSbtPlugin("org.ensime" % "sbt-ensime" % "$sbtEnsimeVersion")""")
  // create src/main/scala
  mkdir! wd/'src/'main/'scala
  // create symbolic link from .sc scripts to .scala files in src/main/scala
  ls! wd |? (_.ext == "sc") | (fil => ln.s(fil,Path(fil.last.replaceAll("\\.[^.]*$", "") + ".scala",wd/'src/'main
/'scala)))
  // run sbt for ensime if target is not sbt
  target match {
    case "ensime" => %sbt 'ensimeConfig
    case "sbt" => println("no config for sbt")
    case _ => println("bad target parameter")
   }
}

@main
def unEnsimize() = {
  rm! wd/".ensime_cache"
  rm! wd/'project
  rm! wd/'src
  rm! wd/'target
  rm! wd/".ensime"
  rm! wd/"build.sbt"
  ls! wd |? (_.ext == "log") | rm
}

@main
def scalaIt(fil:String) = {
  ln.s(Path(fil,wd),Path(fil.replaceAll("\\.[^.]*$", "") + ".scala",wd/'src/'main/'scala))
}
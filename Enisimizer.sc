@doc("Ensimizer allows you to ensime-ize an Ammonite script project directory so you can edit the scripts as Scala code in many text editor")
// TO DO make Ensimizer a main function
// TO DO create function just to link and move a sc file to src/main/scala so can move newly added scripts
// TO DO paramaterize the vals at top
// TO DO create .gitignore file so can check-in scripts w/o all the sbt/ensime overheads and link files
// TO DO 
val wd = pwd

@main
def ensimize() = {
  //create build.sbt
  val projname = wd.last
  val sversion = "2.12.2"
  val sbtversion = "0.13.15"
  write(wd/"build.sbt",s"""lazy val root = (project in file("."))
                    .settings(
                      name := \"$projname\",
                      scalaVersion := \"$sversion\"
                    )""")
  // create project/build.properties
  write(wd/'project/"build.properties",s"sbt.version=$sbtversion\n")
  // create src/main/scala
  mkdir! wd/'src/'main/'scala
  // create symbolic link from .sc scripts to .scala files in src/main/scala
  ls! wd |? (_.ext == "sc") | (fil => ln.s(fil,Path(fil.last.replaceAll("\\.[^.]*$", "") + ".scala",wd/'src/'main
/'scala)))
  // run sbt for ensime
  %sbt 'ensimeConfig
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
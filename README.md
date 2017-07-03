# Ensimizer
## Introduction
Ensimizer allows you to ensime-ize an Ammonite script project directory so you can edit the scripts as Scala code in text editors that work with [Ensime](http://ensime.org/). It uses [Ensime's sbt plugin](http://ensime.org/build_tools/sbt/). The only editor this has been tested on is Visual Studio Code, but there is no reason it shouldn't work in other editors. **NB:** keep in mind that because Ammonite is not pure Scala, the Ensime code highighting won't work as well as it does for regular Scala files.
## How It Works
Essentially the script creates an sbt project structure, and then creates links to the Ammonite scripts in the `src/main/scala` directory. These links have a `.scala` extension so when you open those files in your Ensime supporting text editor, they are recognized as `scala` files.

In order for this to work you need to first add the Ensime sbt plugin to the appropriate sbt file  as per the instructions [here](http://ensime.org/build_tools/sbt/#install). The last line of the script runs `sbt ensimeConfig` so you don't have to.

## Current Caveats
In this version of the script, there are several things you need to do manually. In future versions the script will provide ways to do this for you.
1. If you want to check in your scripts into github without all the added scaffolding, copy the `.gitignore` file from the cloned project into your project directory. The ensimizer project was used on itself and as you can see only the script is in the project.
1. If you add more scripts to the project directory after running the script the first time, you need to manually add links with the `.scala` extension.


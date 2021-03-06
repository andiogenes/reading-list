package org.flovver.cmd.cli

object App {
  val usage: String =
    """ Usage: flovver [command]
      |
      | Commands:
      |   help              prints this text
      |   new [project]     creates new Flovver project of name [project]
      |   ide [directory]   starts IDE session for a given project folder
      |   build [directory] builds project located in a provided folder
      |""".stripMargin

  // Specify command-line interface actions
  var helpCommand: () => Unit = () => println(usage)
  var newCommand: String => Unit = Actions.scaffoldProject
  var ideCommand: String => Unit = Actions.launchIde
  var buildCommand: String => Unit = Actions.buildProject

  def run(args: List[String]): Unit = {
    args match {
      case "new" :: project :: Nil => newCommand(project)
      case "ide" :: project :: Nil => ideCommand(project)
      case "ide" :: Nil => ideCommand(".")
      case "build" :: project :: Nil => buildCommand(project)
      case "build" :: Nil => buildCommand(".")
      case _ => helpCommand()
    }
  }
}

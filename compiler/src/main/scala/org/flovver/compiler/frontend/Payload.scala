package org.flovver.compiler.frontend

case class Project(name: String)
case class Compiler(flags: Flags)
case class Flags(`tail-call-elimination`: Boolean, `fibonacci-elimination`: Boolean, `common-recursion-memoization`: Boolean, debug: Boolean)
case class Model(`model-type`: String, `message-type`: String, types: List[Type])
case class Type(base: String, alias: String, variants: Option[List[Variant]])
case class Variant(name: String, `type`: String)
case class View(pane: Pane, widgets: List[Widget])
case class Pane(width: Int, height: Int)
case class Widget(`type`: String, caption: String, x: Int, y: Int, width: Option[Int], height: Option[Int], oninput: String, onclick: String)
case class Update(nodes: List[Node], relationships: List[Relationship])
case class Node(`type`: String, name: Option[String], inputs: List[String], output: Option[String], parent: Option[Int])
case class Relationship(`def`: Int, parameter: Option[Int], use: Int, `use-arg`: Int, `pass-by`: String, location: String)
case class Payload(project: Project, compiler: Compiler, model: Model, view: View, update: Update)

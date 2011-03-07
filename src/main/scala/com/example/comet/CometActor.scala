// JNDI: This class taken from: http://www.assembla.com/wiki/show/liftweb/Comet_Support
package com.example {
package comet {

import net.liftweb._
import http._
import SHtml._ 
import net.liftweb.common.{Box, Full}
import net.liftweb.util._
import net.liftweb.actor._
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds.{SetHtml}
import net.liftweb.http.js.JE.Str



class CometMessage extends CometActor {
	
  override def defaultPrefix = Full("comet")
		
  def render = bind("message" -> <span id="message">Whatever you feel like returning</span>)
		
	// Users/richard/tmp/shutdown-test/src/main/scala/com/example/comet/CometActor.scala:22: value ActorPing in package util is deprecated: Use Schedule
  ActorPing.schedule(this, Message, 10000L)
		
	/*
	One for another day, but....
	
[error] /Users/richard/tmp/shutdown-test/src/main/scala/com/example/comet/CometActor.scala:24: type mismatch;
[error]  found   : net.liftweb.http.js.JE.Str
[error]  required: scala.xml.NodeSeq
[error]       partialUpdate(SetHtml("message", Str("updated: " + timeNow.toString)))

for now I've just dropped in Text() in lowPriority
	*/	
		
  override def lowPriority : PartialFunction[Any,Unit] = {
    case Message => {
      partialUpdate(SetHtml("message", scala.xml.Text("updated: " + timeNow.toString)))
      //partialUpdate(SetHtml("message", Str("updated: " + timeNow.toString)))
      ActorPing.schedule(this, Message, 10000L)
    }
  }
}
case object Message

}
}
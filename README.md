Behaviour
---------

When using a JNDI data source and Jetty, shutting down Jetty causes Lift to throw an exception if there are active comet actors.

	2011-03-07 21:41:51,510 WARN  - Failure in remove session
	java.lang.NullPointerException: Looking for Connection Identifier ConnectionIdentifier(jdbc/exampleDb) but failed to find either a JNDI data source with the name jdbc/exampleDb or a lift connection manager with the correct name
	at net.liftweb.db.DB$$anonfun$7$$anonfun$apply$11.apply(DB.scala:154)
	at net.liftweb.db.DB$$anonfun$7$$anonfun$apply$11.apply(DB.scala:154)
	at net.liftweb.common.EmptyBox.openOr(Box.scala:561)

Observed in a production Jetty+MYSQL environment, and reproduced here using a Derby and SBT.

This doesn't appear to have any decremental effect on the application.

Expected behaviour
------------------

When shutting down Jetty (via the Jetty stop script or SBT jetty-stop) we expect no error to be generated.


Steps to reproduce
------------------

This git project is a SBT 0.7.5.RC0 projects created with Lifty for LIFT 2.3-SNAPSHOT running under Scala 2.8.1

	$ git clone shutdown-test
	$ cd shutdown-test
	$ sbt
	> update
	> jetty-run

Ensure that the output from jetty-run includes "JNDI connection found".

Then visit http://127.0.0.1:8080/

Finally, run...

	> jetty-stop

See the stack trace?

Observations
------------

* Commenting out the comet actor in index.html prevents the stack trace being produced during shutdown.
* Using non-JNDI data sources prevents the stack trace.
* Note that the app does not access the data source (no schemify, no mapper access)
* Tested with MySQL, and MySQL with C3P0 polling - produces the stack trace.
* Grep the project for "JNDI" for places I have touched the default Lifty app.




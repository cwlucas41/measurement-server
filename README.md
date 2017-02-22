# measurement-server
Network Programming: project 1

To build and run unit tests:

$ mvn package

Note: maven is not available on hopper at this time. It is available on the linuxlab computers and linuxclassroom computers. So, it it possible to build the project on one of those machines and run the artifacts on hopper itself.

To run echo project:
--------------------

$ java -cp target/measurement-server-0.0.1-SNAPSHOT.jar com.chriswlucas.echo.EchoServer \<port number>

$ java -cp target/measurement-server-0.0.1-SNAPSHOT.jar com.chriswlucas.echo.EchoClient \<server address> \<port number>

To run measure project:
-----------------------

$ java -cp target/measurement-server-0.0.1-SNAPSHOT.jar com.chriswlucas.measure.MeasureServer \<port number>

$ java -cp target/measurement-server-0.0.1-SNAPSHOT.jar com.chriswlucas.measure.MeasureClient \<server address> \<port number>

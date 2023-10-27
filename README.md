```sh
$ gcloud auth application-default login
$ scala-cli main.scala -- your_project_id database_id $(gcloud auth application-default print-access-token)

Compiling project (Scala 3.3.0, JVM)
Warning: there was 1 deprecation warning; re-run with -deprecation for details
Compiled project (Scala 3.3.0, JVM)
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
projects/tanishiking-dev/databases/ember/documents/jokes/3hzB2ZWM0wkOKiiR8Gcz
HTTP/2.0 POST https://firestore.googleapis.com/$rpc/google.firestore.v1.Firestore/GetDocument Headers(te: trailers, grpc-encoding: identity, grpc-accept-encoding: identity, Authorization: Bearer ... Content-Type: application/application/x-protobuf) body="Q
Mprojects/tanishiking-dev/databases/ember/documents/jokes/3hzB2ZWM0wkOKiiR8Gcz*"
HTTP/2.0 400 Bad Request Headers(vary: X-Origin, vary: Referer, vary: Origin,Accept-Encoding, content-type: application/x-protobuf, date: Fri, 27 Oct 2023 11:51:48 GMT, server: ESF, cache-control: private, x-xss-protection: 0, x-frame-options: SAMEORIGIN, x-content-type-options: nosniff, alt-svc: h3=":443"; ma=2592000,h3-29=":443"; ma=2592000, accept-ranges: none) body=,Invalid resource field value in the request.�
(type.googleapis.com/google.rpc.ErrorInfo�
RESOURCE_PROJECT_INVALIDgoogleapis.com3
method)google.firestore.v1.Firestore.GetDocument#
servicefirestore.googleapis.com"
java.util.NoSuchElementException
        at fs2.Stream$CompileOps.lastOrError$$anonfun$1$$anonfun$1(Stream.scala:4918)
        at scala.Option.fold(Option.scala:263)
        at fs2.Stream$CompileOps.lastOrError$$anonfun$1(Stream.scala:4918)
        at flatMap @ fs2.Compiler$Target.flatMap(Compiler.scala:163)
        at flatMap @ fs2.Compiler$Target.flatMap(Compiler.scala:163)
        at flatMap @ fs2.Compiler$Target.flatMap(Compiler.scala:163)
        at flatMap @ fs2.Compiler$Target.flatMap(Compiler.scala:163)
        at flatMap @ fs2.Compiler$Target.flatMap(Compiler.scala:163)
        at flatMap @ fs2.Compiler$Target.flatMap(Compiler.scala:163)
        at modify @ fs2.internal.Scope.close(Scope.scala:262)
        at flatMap @ fs2.Compiler$Target.flatMap(Compiler.scala:163)
        at rethrow$extension @ fs2.Compiler$Target.compile$$anonfun$1(Compiler.scala:157)
        at as @ fs2.io.net.SocketGroupCompanionPlatform$AsyncSocketGroup.connect$1$$anonfun$1$$anonfun$1(SocketGroupPlatform.scala:76)
        at get @ org.http4s.ember.core.h2.H2Client$.impl$$anonfun$1(H2Client.scala:343)
        at flatMap @ fs2.Compiler$Target.flatMap(Compiler.scala:163)
        at flatMap @ fs2.Pull$.goCloseScope$1$$anonfun$1$$anonfun$3(Pull.scala:1217)
        at update @ org.http4s.ember.core.h2.H2Connection.readLoop$$anonfun$1(H2Connection.scala:549)
        at flatMap @ fs2.Compiler$Target.flatMap(Compiler.scala:163)
        at flatMap @ fs2.Compiler$Target.flatMap(Compiler.scala:163)
```
//> using scala 3
//> using dep "org.http4s::http4s-ember-client:0.23.23"
//> using dep "org.typelevel::log4cats-slf4j:2.6.0"
//> using dep "io.chrisdavenport::http4s-grpc-google-cloud-firestore-v1:3.15.2+0.0.6"
// //> using dep "com.google.auth:google-auth-library-oauth2-http:1.20.0"

import cats.syntax.all._
import cats.effect._
import org.http4s._

import org.typelevel.log4cats.LoggerFactory
import org.typelevel.log4cats.slf4j.Slf4jFactory
import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.client.middleware.Logger

import com.google.firestore.v1.firestore.Firestore
import com.google.firestore.v1.document.Document
import com.google.firestore.v1.firestore.GetDocumentRequest
import com.google.firestore.v1.firestore.GetDocumentRequest.ConsistencySelector

// import com.google.auth.oauth2.GoogleCredentials
import org.http4s.Header.ToRaw
import org.typelevel.ci.CIString
import com.google.protobuf.timestamp.Timestamp


object Main extends IOApp:
  override def run(args: List[String]): IO[ExitCode] =
    val projectId: String = args(0)
    val databaseId: String = args(1)
    val accessToken: String = args(2)
    createClient().use { rawClient =>
      // println(s"projectId: $projectId")
      // val adc = GoogleCredentials.getApplicationDefault()
      // adc.refresh()
      val client = Logger[IO](
        logHeaders = true,
        logBody = true,
        redactHeadersWhen = _ => false,
        logAction = Some(msg => IO.println(msg))
      )(rawClient)
      val firestore = Firestore.fromClient(
        client,
        Uri
          .fromString("https://firestore.googleapis.com/$rpc")
          .getOrElse(throw new RuntimeException("invalid firestore uri"))
      )
      val docId = "3hzB2ZWM0wkOKiiR8Gcz"
      firestore
        .getDocument(
          GetDocumentRequest.of(
            name = createDocumentName(projectId, databaseId, "jokes", docId),
            mask = None,
            consistencySelector = ConsistencySelector.ReadTime(Timestamp.defaultInstance)
          ),
          Headers.of(
            headers.Authorization(Credentials.Token(AuthScheme.Bearer, accessToken)),
            headers.`Content-Type`(new MediaType("application", "application/x-protobuf"))
          )
        )
        .flatMap { doc =>
          IO.println(doc)
        } >> ExitCode.Success.pure[IO]
    }

  private implicit val loggerFactory: LoggerFactory[IO] =
    Slf4jFactory.create[IO]

  def createDocumentName(
      projectId: String,
      databaseId: String,
      collectionId: String,
      jokeId: String
  ): String = {
    val documentPath = s"projects/$projectId/databases/$databaseId/documents"
    val res= s"$documentPath/$collectionId/$jokeId"
    println(res)
    res
  }

  def createClient(): Resource[IO, Client[IO]] =
    EmberClientBuilder
      .default[IO]
      .withHttp2
      .build

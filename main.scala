//> using scala 3
//> using dep "org.http4s::http4s-ember-client:0.23.23"
//> using dep "io.chrisdavenport::http4s-grpc-google-cloud-firestore-v1:3.15.2+0.0.6"

import cats.syntax.all._
import cats.effect._
import org.http4s._

import org.http4s.client.Client
import org.http4s.ember.client.EmberClientBuilder
import org.http4s.client.middleware.Logger

import com.google.firestore.v1.firestore.Firestore
import com.google.firestore.v1.document.Document
import com.google.firestore.v1.firestore.GetDocumentRequest
import com.google.firestore.v1.firestore.GetDocumentRequest.ConsistencySelector
import com.google.firestore.v1.firestore.CreateDocumentRequest
import com.google.firestore.v1.document.Value
import com.google.firestore.v1.document.Value.ValueTypeOneof

object Main extends IOApp:
  override def run(args: List[String]): IO[ExitCode] =
    val projectId: String = args(0)
    val accessToken: String = args(1)
    createClient().use { rawClient =>
      val client = Logger[IO](
        logHeaders = true,
        logBody = true,
        redactHeadersWhen = _ => false,
        logAction = Some(msg => IO.println(msg))
      )(rawClient)
      val firestore = Firestore.fromClient(
        client,
        Uri
          .fromString("https://firestore.googleapis.com")
          .getOrElse(throw new RuntimeException("invalid firestore uri"))
      )
      val docId = System.currentTimeMillis().toString
      firestore
        .createDocument(
          CreateDocumentRequest.of(
            parent = s"projects/$projectId/databases/(default)/documents",
            collectionId = "jokes",
            documentId = docId,
            document = Some(Document.of(
              name = "",
              fields = Map(
                "joke" -> Value.of(
                  ValueTypeOneof.StringValue("joke")
                )
              ),
              createTime = None,
              updateTime = None
            )),
            mask = None
          ),
          Headers.of(
            headers.Authorization(
              Credentials.Token(AuthScheme.Bearer, accessToken)
            ),
            headers.`Content-Type`(
              new MediaType("application", "grpc")
            )
          )
        )
        .flatMap { doc =>
          IO.println(doc)
        } >> ExitCode.Success.pure[IO]
    }

  def createClient(): Resource[IO, Client[IO]] =
    EmberClientBuilder
      .default[IO]
      .withHttp2
      .build

import org.scalatest.concurrent.ScalaFutures

class SyslogsServiceSpec extends BaseServiceSpec with ScalaFutures {
  "Syslogs service" should {
    "retrieve syslogs list" in {
      Get("/logs") ~> syslogsRoute ~> check {
        response should be(testSyslogs)
      }
    }
  }
}

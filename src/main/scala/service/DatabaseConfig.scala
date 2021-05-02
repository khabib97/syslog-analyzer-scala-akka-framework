package service

trait DatabaseConfig extends Config {
  val driver = slick.jdbc.H2Profile
  import  driver.api._

  val db: Database = Database.forConfig("database")
  implicit val session :Session = db.createSession()


}

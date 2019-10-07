import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.insert
import me.liuwj.ktorm.entity.Entity
import me.liuwj.ktorm.entity.findById
import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.datetime
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.varchar
import java.nio.file.Paths
import java.time.LocalDateTime

fun main() {
    val path = Paths.get(System.getProperty("user.home"), "ktorm_repro.sqlite")
    Database.connect("jdbc:sqlite:${path}", driver = "org.sqlite.JDBC")
    Employees.insert {
        it.name to "John Doe"
        it.hiredAt to LocalDateTime.now()
    }
    println(Employees.findById(1)?.name)
}

interface Employee : Entity<Employee> {
    val id: Int?
    var name: String
    var hiredAt: LocalDateTime
}

object Employees : Table<Employee>("employees") {
    val id by int("id").primaryKey().bindTo { it.id }
    val name by varchar("name").bindTo { it.name }
    val hiredAt by datetime("hired_at").bindTo { it.hiredAt }
}

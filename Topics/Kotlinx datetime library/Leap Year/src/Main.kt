import kotlinx.datetime.*

fun isLeapYear(year: String): Boolean {
    return try {
        val instance1 = Instant.parse("$year-02-29T00:00:00Z")
        true
    } catch (e: Exception) {
        false
    }
}

fun main() {
    val year = readln()
    println(isLeapYear(year))
}
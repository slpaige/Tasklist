import kotlinx.datetime.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

fun nextMonth(date: String): String {
    // Write your code here
    val instant1 = LocalDateTime.parse(date)
    val dateTime = LocalDateTime.ofInstant(instant1.toInstant(ZoneOffset.UTC), ZoneId.systemDefault()).plusMonths(1)
    return dateTime.toString()
}

fun main() {
    val date = readln()
    println(nextMonth(date))
}
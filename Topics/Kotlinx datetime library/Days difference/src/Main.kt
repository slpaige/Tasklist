import kotlinx.datetime.*

fun howManyDays(year1: Int, month1: Int, day1: Int, year2: Int, month2: Int, day2: Int): Int {
    val instant1 = Instant.parse("${year1}-${month1.toString().padStart(2,'0')}-${day1.toString().padStart(2,'0')}T00:00:00Z")
    val instant2 = Instant.parse("$year2-${month2.toString().padStart(2,'0')}-${day2.toString().padStart(2,'0')}T00:00:00Z")
    return instant1.until(instant2, DateTimeUnit.DAY, TimeZone.UTC).toInt()
}

fun main() {
    val (year1, month1, day1) = readln().split(" ").map { it.toInt() }
    val (year2, month2, day2) = readln().split(" ").map { it.toInt() }

    println(howManyDays(year1, month1, day1, year2, month2, day2))
}
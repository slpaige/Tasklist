import java.util.*

fun solution(strings: MutableList<String>, str: String): MutableList<String> {
    // put your code here
    Collections.replaceAll(strings, str, "Banana")
    return strings
}
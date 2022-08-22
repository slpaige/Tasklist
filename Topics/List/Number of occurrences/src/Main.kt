fun solution(strings: List<String>, str: String): Int {
    // put your code here
    val count = strings.filter {item -> item == str}
    return count.count()
}
fun main() {
    // write your code here

    val nLists = MutableList(readln().toInt()) { t -> readln().toInt() }
    val checkNums = readln().split(" ").map { t -> t.toInt() }.toMutableList()

    println(
        if (nLists.containsAll(checkNums)) {
            "YES"
        } else {
            "NO"
        }
    )
}
fun main() {
    // write your code here

    val nLists = MutableList(readln().toInt()) { t -> readln().toInt() }
    val mNumber = readln().toInt()

    println(
        if (nLists.contains(mNumber)) {
            "YES"
        } else {
            "NO"
        }
    )
}
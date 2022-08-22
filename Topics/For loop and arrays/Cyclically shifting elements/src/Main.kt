fun main() {
    // write your code here
    val numElements = readln().toInt()

    var intArray = MutableList<Int>(numElements){t -> 0}
    var index = 0
    repeat(numElements) {
        intArray.set(index++, readln().toInt())
    }

    java.util.Collections.rotate(intArray,1)
    println(intArray.joinToString(" "))

}
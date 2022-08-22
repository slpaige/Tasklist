import java.lang.System.`in`
import java.util.*

fun main() {
    // write your code here
    val scanner = Scanner(`in`)
    var numArray = IntArray(scanner.nextInt())
    var total = 0
    var count = 0
    for (i in 0..numArray.lastIndex - 2) {
        total += follow(i, numArray[count+1],numArray[count+2] )
        count++
    }

    println(total)
}

fun follow(x: Int, y: Int, z: Int) = if (y - x == 1 && z - y == 1) 1 else 0
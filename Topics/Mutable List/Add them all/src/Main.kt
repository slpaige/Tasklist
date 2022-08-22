fun solution(first: List<Int>, second: List<Int>): MutableList<Int> {
    // put your code here
    val new = first.toMutableList()
    new.addAll(second)
    return new
}
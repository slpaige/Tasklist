fun solution(numbers: List<Int>, number: Int): MutableList<Int> {
    // put your code here
    var numbersMut = mutableListOf<Int>();
    numbersMut.addAll(numbers)
    numbersMut.add(number)
    return numbersMut
}
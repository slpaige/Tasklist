fun solution(products: List<String>, product: String) {
    // put your code here
    for (i in products.withIndex()) {
        if (i.value == product) {
            print(i.index)
            print(" ")
        }
    }
}
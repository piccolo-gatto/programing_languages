fun check_operator(elem: String): Boolean {
    return elem in arrayListOf<String>("+", "*", "-", "/")
}

fun check_operand(elem: String): Boolean {
    return elem.toIntOrNull() != null
}

fun to_infix(elems: String): Any {
    val elems = elems.split(" ")
    val stack = arrayListOf<String>()
    for (elem in elems.reversed()){
        if (check_operand(elem)){
            stack.add(elem)
        }
        else if (check_operator(elem)) {

            if (stack.size < 2) {
                return "Invalid expression"
            }
            val operand1 = stack.last()
            stack.remove(stack.last())
            val operand2 = stack.last()
            stack.remove(stack.last())
            val expression = "($operand1 $elem $operand2)"
            stack.add(expression)
        }else {
            return "Invalid token"
        }
    }
    if (stack.size != 1) {
        return "Invalid expression"
    }
    return stack.first()
}


fun main(){
    val prefix_notations = arrayListOf<String>(
            "+ - 13 4 55",
            "+ 2 * 2 - 2 1",
            "+ + 10 20 30",
            "- - 1 2",
            "/ + 3 10 * + 2 3 - 3 5")
    for (notation in prefix_notations){
        println(to_infix(notation))
    }
}
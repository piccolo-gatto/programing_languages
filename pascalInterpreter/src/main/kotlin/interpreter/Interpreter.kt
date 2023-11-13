package interpreter

class Interpreter {
    private val parser = Parser()
    val variables = mutableMapOf<String, Double>()

    fun visit(node: Node): Double {
        when (node) {
            is Number -> return visitNumber(node)
            is BinOp -> return visitBinOp(node)
            is Variable -> visitVariable(node)
            is Semicolon -> visitSemicolon(node)
            is Empty -> {}
            is Identifier -> return visitIdentifier(node)
            is UnarOp -> return visitUnarOp(node)
        }
        return 0.0
    }

    fun visitNumber(number: Number): Double {
        return number.token.value.toDouble()
    }

    fun visitBinOp(node: BinOp): Double {
        return when (node.op.value) {
            "+" -> visit(node.left) + visit(node.right)
            "-" -> visit(node.left) - visit(node.right)
            "*" -> visit(node.left) * visit(node.right)
            "/" -> visit(node.left) / visit(node.right)
            else -> throw IllegalArgumentException("Invalid operator")
        }
    }


    fun visitUnarOp(node: UnarOp): Double {
        return when (node.op.value) {
            "+" -> +visit(node.value)
            "-" -> -visit(node.value)
            else -> throw IllegalArgumentException("Invalid operator")
        }
    }

    fun visitIdentifier(node: Identifier): Double {
        if (node.token.value in variables.keys) {
            return variables[node.token.value]!!
        }
        throw IllegalArgumentException("Unknown variable")
    }

    fun visitSemicolon(node: Semicolon) {
        visit(node.left)
        visit(node.right)
    }

    fun visitVariable(node: Variable) {
        if (node.token.value !in variables.keys) {
            variables[node.token.value] = 0.0
        }
        variables[node.token.value] = visit(node.value)
    }

    fun eval(code: String): Double {
        val tree = parser.parse(code)
        return visit(tree)
    }
}
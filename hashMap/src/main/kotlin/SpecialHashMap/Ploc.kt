package SpecialHashMap


class Ploc<ValueType>(private val specialHashMap: SpecialHashMap<ValueType>) {
    operator fun get(requirement: String): Map<String, ValueType> {
        val requirements = parseParam(requirement)
        return specialHashMap.filterKeys {
            val splitKey = parseParam(it)
            if (requirements.size == splitKey.size) {
                for (i in 0..requirements.size - 1) {
                    val operator = parseOperator(requirements[i])
                    val rightNumber = requirements[i].replace(operator, "").toDouble()
                    val leftNumber = splitKey[i].toDouble()
                    when (operator) {
                        ">" -> {
                            if (leftNumber <= rightNumber) {
                                return@filterKeys false
                            }
                        }
                        "<" -> {
                            if (leftNumber >= rightNumber) {
                                return@filterKeys false
                            }
                        }
                        ">=" -> {
                            if (leftNumber < rightNumber) {
                                return@filterKeys false
                            }
                        }
                        "<=" -> {
                            if (leftNumber > rightNumber) {
                                return@filterKeys false
                            }
                        }
                        "=" -> {
                            if (leftNumber != rightNumber) {
                                return@filterKeys false
                            }
                        }
                        "<>" -> {
                            if (leftNumber == rightNumber) {
                                return@filterKeys false
                            }
                        }
                        else -> {
                            throw IllegalArgumentException("Invalid operator")
                        }
                    }
                }
                return@filterKeys true
            }
            else{
                return@filterKeys false
            }

        }
    }

    private fun parseParam(params: String): List<String> {
        return params.replace(Regex("[()]"), "").split(Regex("[A-Za-zА-Яа-я,:;_]"))
    }

    private fun parseOperator(operators: String): String {
        var operator = ""
        for (char in operators) {
            if (char in charArrayOf('>', '<', '=')) {
                operator += char.toString()
            }
        }
        return operator
    }
}
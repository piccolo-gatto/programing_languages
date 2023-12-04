package SpecialHashMap


class Ploc<ValueType>(private val specialHashMap: SpecialHashMap<ValueType>) {
    operator fun get(condition: String): Map<String, ValueType> {
        val conditions = parseParam(condition)
        return specialHashMap.filterKeys {
            val splitKey = parseParam(it)
            if (conditions.size == splitKey.size) {
                for (i in 0..conditions.size - 1) {
                    val operator = parseOperator(conditions[i])
                    val rightNumber = conditions[i].replace(operator, "").toDouble()
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
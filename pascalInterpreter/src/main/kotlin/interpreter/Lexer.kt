package interpreter

class Lexer(var pos : Int = 0, var text : String = "", var currentChar : Char? = null) {
    fun init(text : String) {
        this.text = text
        pos = 0
        currentChar = this.text[pos]
    }

    fun forward() {
        pos += 1
        if (pos > text.length - 1) {
            currentChar = null
        }
        else {
            currentChar = text[pos]
        }
    }

    fun skip() {
        while (currentChar != null && currentChar!!.isWhitespace()){
            forward()
        }
    }
    fun number(): String {
        val result = mutableListOf<Char>()
        while (currentChar!!.isDigit() || currentChar == '.') {
            result.add(currentChar!!)
            forward()
        }
        return result.joinToString("")
    }

    fun variable(): String {
        val result = mutableListOf<Char>()
        while (currentChar!!.isLetter() || currentChar!!.isDigit()) {
            result.add(currentChar!!)
            forward()
        }
        return result.joinToString("")
    }

    fun next(): Token? {
        while (this.currentChar != null) {
            var currentChar = this.currentChar!!.let { this.currentChar!! }
            if (currentChar.isWhitespace()) {
                skip()
                continue
            }
            if (currentChar == 'B') {
                val begin = arrayListOf<Char>('E', 'G', 'I', 'N')
                val result = mutableListOf<Char>()
                forward()
                for (i in begin) {
                    currentChar = this.currentChar!!.let { this.currentChar!! }
                    result.add(currentChar)
                    forward()
                }
                if (result.toString() == begin.toString()) {
                    return Token(TokenType.BEGIN, "BEGIN")
                }
                throw IllegalArgumentException("Invalid variable")
            }
            if (currentChar.isDigit()) {
                return Token(TokenType.NUMBER, number())
            }
            if (currentChar in arrayListOf<Char>('+', '-', '*', '/')) {
                val op = currentChar
                forward()
                return Token(TokenType.OPERATOR, op.toString())
            }
            if (currentChar in arrayListOf<Char>('(', ')')) {
                val paren = currentChar
                forward()
                return if (paren == '(') {
                    Token(TokenType.LPAREN, paren.toString())
                } else {
                    Token(TokenType.RPAREN, paren.toString())
                }
            }
            if (currentChar == ';') {
                forward()
                return Token(TokenType.SEMICOLON, ";")
            }
            if (currentChar == '.') {
                forward()
                return Token(TokenType.DOT, ".")
            }
            if (currentChar == ':') {
                forward()
                while (this.currentChar!!.isWhitespace()) {
                    forward()
                }
                if (this.currentChar!! == '=') {
                    forward()
                    return Token(TokenType.ASSIGN, "=")
                }
                throw IllegalArgumentException("Invalid syntax")
            }
            if (currentChar == 'E') {
                val end = arrayListOf<Char>('N', 'D')
                val result = mutableListOf<Char>()
                forward()
                for (i in end) {
                    currentChar = this.currentChar!!.let { this.currentChar!! }
                    result.add(currentChar)
                    forward()
                }
                if (result.toString() == end.toString()) {
                    return Token(TokenType.END, "END")
                }
                throw IllegalArgumentException("Invalid variable")
            }
            if (currentChar.isLetter()) {
                return Token(TokenType.ID, variable())
            }
            throw IllegalArgumentException("Invalid token")
        }
        return null
    }
}
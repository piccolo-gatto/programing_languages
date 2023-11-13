package interpreter

class Parser {
    private var currentToken: Token? = null
    private var lexer = Lexer()

    private fun checkToken(type: TokenType) {
        if (currentToken?.type == type) {
            currentToken = lexer.next()
        } else {
            throw IllegalArgumentException("Invalid token")
        }
    }

    private fun factor(): Node {
        val token = currentToken
        when (token!!.type) {
            TokenType.NUMBER -> {
                checkToken(TokenType.NUMBER)
                return Number(token)
            }

            TokenType.LPAREN -> {
                checkToken(TokenType.LPAREN)
                val result = expr()
                checkToken(TokenType.RPAREN)
                return result
            }

            TokenType.OPERATOR -> {
                checkToken(TokenType.OPERATOR)
                return UnarOp(token, factor())
            }

            TokenType.ID -> {
                checkToken(TokenType.ID)
                return Identifier(token)
            }

            else -> {
                throw IllegalArgumentException("Invalid factor")
            }
        }
    }

    private fun term(): Node {
        val result = factor()
        while (currentToken!!.type == TokenType.OPERATOR) {
            if (currentToken!!.value !in "*/") {
                break
            }
            val token = currentToken
            checkToken(TokenType.OPERATOR)
            return BinOp(result, token!!, term())
        }
        return result
    }

    private fun expr(): Node {
        var result = term()
        while (currentToken!!.type == TokenType.OPERATOR) {
            val token = currentToken
            checkToken(TokenType.OPERATOR)
            result = BinOp(result, token!!, term())
        }
        return result
    }

    private fun variable(): Token {
        val result = currentToken
        checkToken(TokenType.ID)
        return result!!
    }

    private fun assignment(): Node {
        val idToken = variable()
        checkToken(TokenType.ASSIGN)
        return Variable(idToken, expr())
    }

    private fun statementList(): Node {
        var result = statement()
        if (currentToken!!.type == TokenType.SEMICOLON) {
            checkToken(TokenType.SEMICOLON)
            result = Semicolon(result, statementList())
        }
        return result
    }

    private fun complexStatement(): Node {
        checkToken(TokenType.BEGIN)
        val result = statementList()
        checkToken(TokenType.END)
        return result
    }

    private fun empty(): Node {
        return Empty()
    }

    private fun program(): Node {
        val result = complexStatement()
        checkToken(TokenType.DOT)
        return result
    }

    private fun statement(): Node {
        return when (currentToken?.type) {
            TokenType.BEGIN -> complexStatement()
            TokenType.ID -> assignment()
            TokenType.END -> empty()
            else -> throw IllegalArgumentException("Invalid token")
        }
    }

    fun parse(code: String): Node {
        lexer.init(code)
        currentToken = lexer.next()
        return program()
    }
}
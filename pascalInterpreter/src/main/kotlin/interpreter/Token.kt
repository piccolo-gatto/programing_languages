package interpreter

enum class TokenType() {
    NUMBER, OPERATOR, EOL, LPAREN, RPAREN, BEGIN, END, DOT, SEMICOLON, ASSIGN, ID
}

class Token(var type: TokenType, var value: String)
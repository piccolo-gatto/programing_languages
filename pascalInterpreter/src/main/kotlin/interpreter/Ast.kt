package interpreter

abstract class Node(){
}

class Number(val token: Token) : Node()
class BinOp(val left: Node, val op: Token, val right: Node) : Node()
class UnarOp(val op: Token, val value: Node) : Node()
class Empty(val value: String = "") : Node()
class Variable(val token: Token, val value: Node) : Node()
class Identifier(val token: Token) : Node()
class Semicolon(val left: Node, val right: Node) : Node()
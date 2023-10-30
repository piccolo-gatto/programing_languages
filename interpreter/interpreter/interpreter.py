from .parser import Parser
from .ast import Number, BinOp, UnarOp

class NodeVisitor:
    
    def visit(self):
        pass

class Interpreter(NodeVisitor):
    
    def __init__(self):
        self.parser = Parser()

    def visit(self, node):
        if isinstance(node, Number):
            return self.visit_number(node)
        elif isinstance(node, BinOp):
            return self.visit_binop(node)
        elif isinstance(node, UnarOp):
            return self.visit_unarop(node)

    def visit_number(self, node):
        return float(node.token.value)

    def visit_binop(self, node):
        match node.op.value:
            case "+":
                return self.visit(node.left) + self.visit(node.right)
            case "-":
                return self.visit(node.left) - self.visit(node.right)
            case "*":
                return self.visit(node.left) * self.visit(node.right)
            case "/":
                return self.visit(node.left) / self.visit(node.right)
            case _:
                raise ValueError("Invalid operator")

    def visit_unarop(self, node):
        match node.op.value:
            case "+":
                return self.visit(node.node)
            case "-":
                return -self.visit(node.node)
            case _:
                raise ValueError("Invalid operator")

    def eval(self, code):
        tree = self.parser.parse(code)
        return self.visit(tree)

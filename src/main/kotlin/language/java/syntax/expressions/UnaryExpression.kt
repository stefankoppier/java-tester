package language.java.syntax.expressions

class UnaryExpression(val operator: UnaryOperator, val operand: Expression) : Expression() {

    override fun toString(): String {
        return "UnaryExpression{$operator $operand}"
    }
}

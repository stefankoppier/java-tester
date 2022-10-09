package language.java.syntax.expressions

class UnaryExpression(val operator: Operator, val operand: Expression) : Expression() {

    enum class Operator {
        NOT,
    }

    override fun toString(): String {
        return "UnaryExpression{$operator $operand}"
    }
}

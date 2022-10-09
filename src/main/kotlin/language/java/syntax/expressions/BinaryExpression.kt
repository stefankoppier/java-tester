package language.java.syntax.expressions

class BinaryExpression(val lhs: Expression, val operator: Operator, val rhs: Expression) : Expression() {

    enum class Operator {
        EQUALS,
        NOT_EQUALS,
        PLUS,
        MINUS,
        TIMES,
        DIVIDE,
        MODULO,
        LESS_THAN,
        LESS_THAN_EQUALS,
        GREATER_THAN,
        GREATER_THAN_EQUALS,
        AND,
        OR,
    }

    override fun toString(): String {
        return "BinaryExpression{$lhs $operator $rhs}"
    }
}

package language.java.syntax.expressions

class BinaryExpression(val lhs: Expression, val operator: BinaryOperator, val rhs: Expression) :
    Expression() {

    override fun toString(): String {
        return "BinaryExpression{$lhs $operator $rhs}"
    }
}

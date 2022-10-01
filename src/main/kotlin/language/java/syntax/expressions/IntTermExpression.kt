package language.java.syntax.expressions

class IntTermExpression(val value: Int) : Expression() {
    override fun toString(): String {
        return "IntTermExpression{$value}"
    }
}

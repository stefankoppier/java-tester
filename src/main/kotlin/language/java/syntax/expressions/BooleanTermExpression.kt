package language.java.syntax.expressions

class BooleanTermExpression(val value: Boolean) : Expression() {
    override fun toString(): String {
        return "BooleanTermExpression{$value}"
    }
}

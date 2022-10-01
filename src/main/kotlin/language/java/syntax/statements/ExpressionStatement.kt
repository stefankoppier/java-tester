package language.java.syntax.statements

import language.java.syntax.expressions.Expression

class ExpressionStatement(val expression: Expression) : Statement() {
    override fun toString(): String {
        return "ExpressionStatement{$expression}"
    }
}

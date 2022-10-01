package language.java.syntax.statements

import language.java.syntax.expressions.Expression

class ReturnStatement(val value: Expression?) : Statement() {
    override fun toString(): String {
        return "ReturnStatement{$value}"
    }
}

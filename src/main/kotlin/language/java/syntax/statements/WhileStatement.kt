package language.java.syntax.statements

import language.java.syntax.expressions.Expression

class WhileStatement(val guard: Expression, val body: Statement) : Statement() {
    override fun toString(): String {
        return "WhileStatement{$guard $body}"
    }
}

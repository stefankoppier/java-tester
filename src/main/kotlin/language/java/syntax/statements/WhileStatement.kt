package language.java.syntax.statements

import language.java.lexing.Position
import language.java.syntax.expressions.Expression

class WhileStatement(val guard: Expression, val body: Statement, position: Position) : Statement(position) {
    override fun toString(): String {
        return "WhileStatement{$guard $body}"
    }
}

package language.java.syntax.statements

import language.java.lexing.Position
import language.java.syntax.expressions.Expression

class ReturnStatement(val value: Expression?, position: Position) : Statement(position) {
    override fun toString(): String {
        return "ReturnStatement{$value}"
    }
}

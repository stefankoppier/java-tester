package language.java.syntax.statements

import language.java.lexing.Position

class SequenceStatement(val first: Statement, val second: Statement, position: Position) : Statement(position) {
    override fun toString(): String {
        return "SequenceStatement{$first $second}"
    }
}

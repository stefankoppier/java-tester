package language.java.syntax.statements

import language.java.lexing.Position

class BreakStatement(position: Position) : Statement(position) {
    override fun toString(): String {
        return "BreakStatement"
    }
}

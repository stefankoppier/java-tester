package language.java.syntax.statements

import language.java.lexing.Position

class ContinueStatement(position: Position) : Statement(position) {
    override fun toString(): String {
        return "ContinueStatement"
    }
}

package language.java.syntax.statements

import language.java.lexing.Position

class BlockStatement(val body: Statement, position: Position) : Statement(position) {
    override fun toString(): String {
        return "BlockStatement{$body}"
    }
}

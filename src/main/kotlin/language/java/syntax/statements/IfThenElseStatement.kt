package language.java.syntax.statements

import language.java.lexing.Position
import language.java.syntax.expressions.Expression

class IfThenElseStatement(
    val guard: Expression,
    val trueStatement: Statement,
    val falseStatement: Statement?,
    position: Position
) : Statement(position) {

    override fun toString(): String {
        return "IfThenElseStatement{$guard $trueStatement $falseStatement}"
    }
}

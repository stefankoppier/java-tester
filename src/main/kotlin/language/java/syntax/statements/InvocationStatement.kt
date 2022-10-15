package language.java.syntax.statements

import language.java.lexing.Position
import language.java.syntax.Identifier
import language.java.syntax.expressions.Expression
import language.java.syntax.type.NonVoidType

class InvocationStatement(
    val type: NonVoidType,
    val lhs: Identifier,
    val name: Identifier,
    val arguments: List<Expression>,
    position: Position
) : Statement(position) {
    override fun toString(): String {
        return "InvocationStatement{$type $lhs $name $arguments}"
    }
}

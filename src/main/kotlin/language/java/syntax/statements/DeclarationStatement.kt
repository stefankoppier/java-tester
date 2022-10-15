package language.java.syntax.statements

import language.java.lexing.Position
import language.java.syntax.*
import language.java.syntax.expressions.Expression
import language.java.syntax.type.NonVoidType

class DeclarationStatement(
    val type: NonVoidType,
    val name: Identifier,
    val value: Expression? = null,
    position: Position
) : Statement(position) {
    override fun toString(): String {
        return "Declaration{$type $name $value}"
    }
}

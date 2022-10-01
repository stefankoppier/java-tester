package language.java.syntax.statements

import language.java.syntax.*
import language.java.syntax.expressions.Expression
import language.java.syntax.type.NonVoidType

class DeclarationStatement(
    val type: NonVoidType,
    val identifier: Identifier,
    val value: Expression? = null
) : Statement() {
    override fun toString(): String {
        return "Declaration{$type $identifier $value}"
    }
}

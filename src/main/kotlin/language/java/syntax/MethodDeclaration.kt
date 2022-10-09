package language.java.syntax

import language.java.syntax.statements.Statement
import language.java.syntax.type.Type

class MethodDeclaration(
    val modifiers: List<Modifier>,
    val type: Type,
    val name: Identifier,
    val parameters: List<Parameter>,
    val body: Statement
) {
    override fun toString(): String {
        return "MethodDeclaration{$modifiers $type $name $parameters $body}"
    }
}

package semantics.errors

import java.lang.String.format
import language.java.syntax.Identifier

class WriteToUndeclaredVariableError(identifier: Identifier) :
    SemanticError(
        format(
            format, identifier.identifier, identifier.position.line, identifier.position.column)) {

    companion object {
        private const val format =
            "Assignment to undeclared variable '%s' at line '%d' and column '%d'"
    }
}

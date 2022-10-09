package semantics.errors

import java.lang.String.format
import language.java.syntax.Identifier

class UseOfUndeclaredVariableError(identifier: Identifier) :
    SemanticError(format(format, identifier.identifier, identifier.position.line, identifier.position.column)) {

    companion object {
        private const val format = "Use of undeclared variable '%s' at line '%d' and column '%d'"
    }
}

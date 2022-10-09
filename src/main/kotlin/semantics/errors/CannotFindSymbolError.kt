package semantics.errors

import language.java.syntax.Identifier

class CannotFindSymbolError(val identifier: Identifier) :
    SemanticError(
        if (identifier.position.isUnknown()) String.format(formatUnknownPosition, identifier.identifier)
        else
            String.format(
                formatKnownPosition,
                identifier.identifier,
                identifier.position.line,
                identifier.position.column
            )
    ) {

    companion object {
        private const val formatKnownPosition = "Cannot find symbol '%s' at line '%d' and column '%d'"

        private const val formatUnknownPosition = "Cannot find symbol '%s'"
    }
}

package language.java.syntax

import language.java.lexing.Position

class Identifier(val identifier: String, val position: Position) {
    override fun toString(): String {
        return "Identifier{$identifier, $position}"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Identifier) identifier == other.identifier else false
    }

    override fun hashCode(): Int {
        return identifier.hashCode()
    }
}

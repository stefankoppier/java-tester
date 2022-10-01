package language.java.lexing

import java.util.*

class Position(val line: Int, val column: Int) {
    companion object {
        fun unknown(): Position {
            return Position(-1, -1)
        }
    }

    override fun toString(): String {
        return "Position{line=$line column=$column}"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Position) this.line == other.line && this.column == other.column
        else false
    }

    override fun hashCode(): Int {
        return Objects.hash(line, column)
    }
}

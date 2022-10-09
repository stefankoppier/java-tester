import language.java.lexing.Position
import language.java.syntax.Identifier

class ApplicationConfiguration {
    companion object {
        private var entry: String? = null

        fun entry(): Identifier? {
            return if (entry != null) Identifier(entry!!, Position.unknown()) else null
        }

        fun entry(value: String) {
            entry = value
        }
    }
}

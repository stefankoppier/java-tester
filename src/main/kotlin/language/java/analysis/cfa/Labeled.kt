package language.java.analysis.cfa

class Labeled<T>(val label: Int, val content: T) {
    companion object {
        fun <T> of(label: Int, content: T): Labeled<T> {
            return Labeled(label, content)
        }
    }

    override fun toString(): String {
        return "Labeled $label $content"
    }
}

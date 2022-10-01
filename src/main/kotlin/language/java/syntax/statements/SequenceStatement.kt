package language.java.syntax.statements

class SequenceStatement(val first: Statement, val second: Statement) : Statement() {
    override fun toString(): String {
        return "SequenceStatement{$first $second}"
    }
}

package language.java.syntax.statements

class BlockStatement(val body: Statement) : Statement() {
    override fun toString(): String {
        return "BlockStatement{$body}"
    }
}

package language.java.syntax.expressions

import language.java.syntax.Identifier

class VariableTermExpression(val name: Identifier) : Expression() {
    override fun toString(): String {
        return "VariableTermExpression{$name}"
    }
}

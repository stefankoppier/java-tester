package language.java.syntax.expressions

import language.java.syntax.Identifier

class VariableTermExpression(val identifier: Identifier) : Expression() {
    override fun toString(): String {
        return "VariableTermExpression{$identifier}"
    }
}

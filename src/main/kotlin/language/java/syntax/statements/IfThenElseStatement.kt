package language.java.syntax.statements

import language.java.syntax.expressions.Expression

class IfThenElseStatement(val guard: Expression, val trueStatement: Statement, val falseStatement: Statement?) :
    Statement() {

    override fun toString(): String {
        return "IfThenElseStatement{$guard $trueStatement $falseStatement}"
    }
}

package language.java.parsing.algebra

import language.java.syntax.Identifier
import language.java.syntax.expressions.Expression
import language.java.syntax.statements.*
import language.java.syntax.type.NonVoidType

interface StatementAlgebra<T> {

    operator fun invoke(statement: Statement): T {
        return when (statement) {
            is BlockStatement -> fBlock(invoke(statement.body))
            is BreakStatement -> fBreak()
            is ContinueStatement -> fContinue()
            is DeclarationStatement -> fDeclaration(statement.type, statement.name, statement.value)
            is EmptyStatement -> fEmpty()
            is ExpressionStatement -> fExpression(statement.expression)
            is IfThenElseStatement -> fIfThenElse(statement.guard, statement.trueStatement, statement.falseStatement)
            is ReturnStatement -> fReturn(statement.value)
            is SequenceStatement -> fSequence(invoke(statement.first), invoke(statement.second))
            is WhileStatement -> fWhile(statement.guard, statement.body)
            is InvocationStatement -> fInvocation(statement.type, statement.lhs, statement.name, statement.arguments)
        }
    }

    fun fBlock(body: T): T

    fun fBreak(): T

    fun fContinue(): T

    fun fDeclaration(type: NonVoidType, name: Identifier, value: Expression?): T

    fun fEmpty(): T

    fun fExpression(value: Expression): T

    fun fIfThenElse(guard: Expression, trueStatement: Statement, falseStatement: Statement?): T

    fun fReturn(value: Expression?): T

    fun fSequence(first: T, second: T): T

    fun fWhile(guard: Expression, body: Statement): T

    fun fInvocation(type: NonVoidType, lhs: Identifier, name: Identifier, arguments: List<Expression>): T
}

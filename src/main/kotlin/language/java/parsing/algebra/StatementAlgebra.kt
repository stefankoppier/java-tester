package language.java.parsing.algebra

import language.java.lexing.Position
import language.java.syntax.Identifier
import language.java.syntax.expressions.Expression
import language.java.syntax.statements.*
import language.java.syntax.type.NonVoidType

interface StatementAlgebra<T> {

    operator fun invoke(statement: Statement): T {
        return when (statement) {
            is BlockStatement -> fBlock(invoke(statement.body), statement.position)
            is BreakStatement -> fBreak(statement.position)
            is ContinueStatement -> fContinue(statement.position)
            is DeclarationStatement -> fDeclaration(statement.type, statement.name, statement.value, statement.position)
            is EmptyStatement -> fEmpty(statement.position)
            is ExpressionStatement -> fExpression(statement.expression, statement.position)
            is IfThenElseStatement ->
                fIfThenElse(
                    statement.guard,
                    invoke(statement.trueStatement),
                    statement.falseStatement?.let { invoke(it) },
                    statement.position
                )
            is ReturnStatement -> fReturn(statement.value, statement.position)
            is SequenceStatement -> fSequence(invoke(statement.first), invoke(statement.second), statement.position)
            is WhileStatement -> fWhile(statement.guard, statement.body, statement.position)
            is InvocationStatement ->
                fInvocation(statement.type, statement.lhs, statement.name, statement.arguments, statement.position)
        }
    }

    fun fBlock(body: T, position: Position): T

    fun fBreak(position: Position): T

    fun fContinue(position: Position): T

    fun fDeclaration(type: NonVoidType, name: Identifier, value: Expression?, position: Position): T

    fun fEmpty(position: Position): T

    fun fExpression(value: Expression, position: Position): T

    fun fIfThenElse(guard: Expression, trueStatement: T, falseStatement: T?, position: Position): T

    fun fReturn(value: Expression?, position: Position): T

    fun fSequence(first: T, second: T, position: Position): T

    fun fWhile(guard: Expression, body: Statement, position: Position): T

    fun fInvocation(
        type: NonVoidType,
        lhs: Identifier,
        name: Identifier,
        arguments: List<Expression>,
        position: Position
    ): T
}

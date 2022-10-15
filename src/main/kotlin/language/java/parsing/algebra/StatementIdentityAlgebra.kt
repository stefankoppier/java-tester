package language.java.parsing.algebra

import language.java.lexing.Position
import language.java.syntax.Identifier
import language.java.syntax.expressions.Expression
import language.java.syntax.statements.*
import language.java.syntax.type.NonVoidType

open class StatementIdentityAlgebra : StatementAlgebra<Statement> {
    override fun fBlock(body: Statement, position: Position): Statement {
        return BlockStatement(body, position)
    }

    override fun fBreak(position: Position): Statement {
        return BreakStatement(position)
    }

    override fun fContinue(position: Position): Statement {
        return ContinueStatement(position)
    }

    override fun fDeclaration(type: NonVoidType, name: Identifier, value: Expression?, position: Position): Statement {
        return DeclarationStatement(type, name, value, position)
    }

    override fun fEmpty(position: Position): Statement {
        return EmptyStatement(position)
    }

    override fun fExpression(value: Expression, position: Position): Statement {
        return ExpressionStatement(value, position)
    }

    override fun fIfThenElse(
        guard: Expression,
        trueStatement: Statement,
        falseStatement: Statement?,
        position: Position
    ): Statement {
        return IfThenElseStatement(guard, trueStatement, falseStatement, position)
    }

    override fun fReturn(value: Expression?, position: Position): Statement {
        return ReturnStatement(value, position)
    }

    override fun fSequence(first: Statement, second: Statement, position: Position): Statement {
        return SequenceStatement(first, second, position)
    }

    override fun fWhile(guard: Expression, body: Statement, position: Position): Statement {
        return WhileStatement(guard, body, position)
    }

    override fun fInvocation(
        type: NonVoidType,
        lhs: Identifier,
        name: Identifier,
        arguments: List<Expression>,
        position: Position
    ): Statement {
        return InvocationStatement(type, lhs, name, arguments, position)
    }
}

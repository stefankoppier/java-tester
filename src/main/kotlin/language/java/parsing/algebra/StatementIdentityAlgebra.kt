package language.java.parsing.algebra

import language.java.syntax.Identifier
import language.java.syntax.expressions.Expression
import language.java.syntax.statements.*
import language.java.syntax.type.NonVoidType

open class StatementIdentityAlgebra : StatementAlgebra<Statement> {
    override fun fBlock(body: Statement): Statement {
        return BlockStatement(body)
    }

    override fun fBreak(): Statement {
        return BreakStatement()
    }

    override fun fContinue(): Statement {
        return ContinueStatement()
    }

    override fun fDeclaration(type: NonVoidType, name: Identifier, value: Expression?): Statement {
        return DeclarationStatement(type, name, value)
    }

    override fun fEmpty(): Statement {
        return EmptyStatement()
    }

    override fun fExpression(value: Expression): Statement {
        return ExpressionStatement(value)
    }

    override fun fIfThenElse(guard: Expression, trueStatement: Statement, falseStatement: Statement?): Statement {
        return IfThenElseStatement(guard, trueStatement, falseStatement)
    }

    override fun fReturn(value: Expression?): Statement {
        return ReturnStatement(value)
    }

    override fun fSequence(first: Statement, second: Statement): Statement {
        return SequenceStatement(first, second)
    }

    override fun fWhile(guard: Expression, body: Statement): Statement {
        return WhileStatement(guard, body)
    }

    override fun fInvocation(
        type: NonVoidType,
        lhs: Identifier,
        name: Identifier,
        arguments: List<Expression>
    ): Statement {
        return InvocationStatement(type, lhs, name, arguments)
    }
}

package language.java.parsing

import language.java.lexing.Position
import language.java.parsing.algebra.ExpressionIdentityAlgebra
import language.java.parsing.algebra.StatementIdentityAlgebra
import language.java.syntax.Identifier
import language.java.syntax.MethodDeclaration
import language.java.syntax.expressions.*
import language.java.syntax.statements.*
import language.java.syntax.type.IntType
import language.java.syntax.type.NonVoidType

class AstSimplifier {

    private var fresh = 0

    private val invocations =
        object : ExpressionIdentityAlgebra() {

            var result: MutableList<InvocationStatement> = mutableListOf()

            override fun invoke(expression: Expression): Expression {
                result = mutableListOf()
                return super.invoke(expression)
            }

            override fun fInvocation(name: Identifier, arguments: List<Expression>): Expression {
                val variable = fresh()
                result.add(InvocationStatement(IntType(name.position), variable, name, arguments, name.position))
                return VariableTermExpression(variable)
            }
        }

    private val algebra =
        object : StatementIdentityAlgebra() {
            override fun fDeclaration(
                type: NonVoidType,
                name: Identifier,
                value: Expression?,
                position: Position
            ): Statement {
                val value = if (value == null) null else invocations(value)
                return invocations.result.fold(DeclarationStatement(type, name, value, position), sequence())
            }

            override fun fExpression(value: Expression, position: Position): Statement {
                val value = invocations(value)
                return invocations.result.fold(ExpressionStatement(value, position), sequence())
            }

            override fun fReturn(value: Expression?, position: Position): Statement {
                val value = if (value == null) null else invocations(value)
                return invocations.result.fold(ReturnStatement(value, position), sequence())
            }

            override fun fIfThenElse(
                guard: Expression,
                trueStatement: Statement,
                falseStatement: Statement?,
                position: Position
            ): Statement {
                val value = invocations(guard)
                return invocations.result.fold(
                    IfThenElseStatement(value, trueStatement, falseStatement, position),
                    sequence()
                )
            }

            private fun sequence() = { acc: Statement, s: Statement -> SequenceStatement(s, acc, s.position) }
        }

    fun simplify(method: MethodDeclaration): MethodDeclaration {
        return MethodDeclaration(method.modifiers, method.type, method.name, method.parameters, algebra(method.body))
    }

    private fun fresh() = Identifier("\$temp${fresh++}", Position.unknown())
}

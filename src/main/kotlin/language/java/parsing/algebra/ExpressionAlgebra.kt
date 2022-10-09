package language.java.parsing.algebra

import language.java.syntax.Identifier
import language.java.syntax.expressions.*

interface ExpressionAlgebra<T> {

    operator fun invoke(expression: Expression): T {
        return when (expression) {
            is AssignmentExpression -> fAssignment(expression.lhs, expression.operator, invoke(expression.value))
            is BinaryExpression -> fBinary(invoke(expression.lhs), expression.operator, invoke(expression.rhs))
            is BooleanTermExpression -> fBooleanTerm(expression.value)
            is IntTermExpression -> fIntTerm(expression.value)
            is InvocationExpression -> fInvocation(expression.name, expression.arguments.map { invoke(it) })
            is UnaryExpression -> fUnary(expression.operator, invoke(expression.operand))
            is VariableTermExpression -> fVariableTerm(expression.name)
        }
    }

    fun fAssignment(lhs: VariableAssignmentTarget, operator: AssignmentExpression.Operator, value: T): T

    fun fBinary(lhs: T, operator: BinaryExpression.Operator, rhs: T): T

    fun fBooleanTerm(value: Boolean): T

    fun fIntTerm(value: Int): T

    fun fInvocation(name: Identifier, arguments: List<T>): T

    fun fUnary(operator: UnaryExpression.Operator, operand: T): T

    fun fVariableTerm(name: Identifier): T
}

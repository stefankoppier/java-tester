package language.java.parsing.algebra

import language.java.syntax.Identifier
import language.java.syntax.expressions.*

open class ExpressionIdentityAlgebra : ExpressionAlgebra<Expression> {

    override fun fAssignment(
        lhs: VariableAssignmentTarget,
        operator: AssignmentExpression.Operator,
        value: Expression
    ): Expression {
        return AssignmentExpression(lhs, operator, value)
    }

    override fun fBinary(lhs: Expression, operator: BinaryExpression.Operator, rhs: Expression): Expression {
        return BinaryExpression(lhs, operator, rhs)
    }

    override fun fBooleanTerm(value: Boolean): Expression {
        return Expression.of(value)
    }

    override fun fIntTerm(value: Int): Expression {
        return Expression.of(value)
    }

    override fun fInvocation(name: Identifier, arguments: List<Expression>): Expression {
        return InvocationExpression(name, arguments)
    }

    override fun fUnary(operator: UnaryExpression.Operator, operand: Expression): Expression {
        return UnaryExpression(operator, operand)
    }

    override fun fVariableTerm(name: Identifier): Expression {
        return VariableTermExpression(name)
    }
}

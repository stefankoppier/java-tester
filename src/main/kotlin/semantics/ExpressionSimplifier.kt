package semantics

import engine.state.State
import language.java.parsing.algebra.ExpressionAlgebra
import language.java.syntax.Identifier
import language.java.syntax.expressions.*
import semantics.errors.UseOfUndeclaredVariableError

class ExpressionSimplifier : ExpressionAlgebra<(State) -> Expression> {

    override fun fAssignment(
        lhs: VariableAssignmentTarget,
        operator: AssignmentExpression.Operator,
        value: (State) -> Expression
    ): (State) -> Expression {
        return { state -> AssignmentExpression(lhs, operator, value(state)) }
    }

    override fun fBinary(
        lhs: (State) -> Expression,
        operator: BinaryExpression.Operator,
        rhs: (State) -> Expression
    ): (State) -> Expression {
        return { state ->
            val lhs = lhs(state)
            val rhs = rhs(state)
            when (operator) {
                BinaryExpression.Operator.EQUALS -> lhs equals rhs
                BinaryExpression.Operator.NOT_EQUALS -> (lhs equals rhs).not()
                BinaryExpression.Operator.PLUS -> lhs plus rhs
                BinaryExpression.Operator.MINUS -> lhs minus rhs
                BinaryExpression.Operator.TIMES -> lhs times rhs
                BinaryExpression.Operator.DIVIDE -> lhs div rhs
                BinaryExpression.Operator.MODULO -> lhs rem rhs
                BinaryExpression.Operator.LESS_THAN -> lhs lessthan rhs
                BinaryExpression.Operator.LESS_THAN_EQUALS -> (lhs lessthan rhs) or (lhs equals rhs)
                BinaryExpression.Operator.GREATER_THAN -> lhs greaterthan rhs
                BinaryExpression.Operator.GREATER_THAN_EQUALS -> (lhs greaterthan rhs) or (lhs equals rhs)
                BinaryExpression.Operator.AND -> lhs and rhs
                BinaryExpression.Operator.OR -> lhs or rhs
            }
        }
    }

    override fun fBooleanTerm(value: Boolean): (State) -> Expression {
        return { _ -> Expression.of(value) }
    }

    override fun fIntTerm(value: Int): (State) -> Expression {
        return { _ -> Expression.of(value) }
    }

    override fun fInvocation(name: Identifier, arguments: List<(State) -> Expression>): (State) -> Expression {
        TODO("Not yet implemented")
    }

    override fun fUnary(operator: UnaryExpression.Operator, operand: (State) -> Expression): (State) -> Expression {
        return { state ->
            when (operator) {
                UnaryExpression.Operator.NOT -> !operand(state)
            }
        }
    }

    override fun fVariableTerm(name: Identifier): (State) -> Expression {
        return { state -> state.callStack.peek()!!.read(name) ?: throw UseOfUndeclaredVariableError(name) }
    }
}

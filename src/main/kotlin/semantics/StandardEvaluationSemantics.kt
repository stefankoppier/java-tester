package semantics

import engine.EvaluationSemantics
import engine.state.State
import language.java.syntax.expressions.*
import semantics.errors.UseOfUndeclaredVariableError
import semantics.errors.WriteToUndeclaredVariableError

class StandardEvaluationSemantics : EvaluationSemantics() {

    override fun evaluate(state: State, expression: Expression): Evaluation<Unit> {
        return when (expression) {
            is AssignmentExpression -> evaluateAssign(state, expression)
            else ->
                throw NotImplementedError(
                    "evaluate of ${expression::class.simpleName}") // Requires a type system
        }
    }

    private fun evaluateAssign(state: State, expression: AssignmentExpression): Evaluation<Unit> {
        val (state, value) = evaluateInt(state, expression.value)

        val stackFrame = state.callStack.peek()
        if (stackFrame!!.read(expression.lhs.identifier) == null) {
            throw WriteToUndeclaredVariableError(expression.lhs.identifier)
        }

        stackFrame.write(expression.lhs.identifier, IntTermExpression(value))
        return Evaluation.of(state, Unit)
    }

    override fun evaluateNullableInt(state: State, expression: Expression?): Evaluation<Int?> {
        return if (expression != null) Evaluation.nullable(evaluateInt(state, expression))
        else Evaluation.of(state, null)
    }

    override fun evaluateInt(state: State, expression: Expression): Evaluation<Int> {
        return when (expression) {
            is BinaryExpression -> evaluateInt(state, expression)
            is IntTermExpression -> evaluateInt(state, expression)
            is VariableTermExpression -> evaluateInt(state, expression)
            else -> throw NotImplementedError("evaluateInt of ${expression::class.simpleName}")
        }
    }

    override fun evaluateBoolean(state: State, expression: Expression): Evaluation<Boolean> {
        return when (expression) {
            is BooleanTermExpression -> evaluateTermBoolean(state, expression)
            is BinaryExpression -> evaluateBinaryBoolean(state, expression)
            is UnaryExpression -> evaluateUnaryBoolean(state, expression)
            else -> throw NotImplementedError("evaluateBoolean of ${expression::class.simpleName}")
        }
    }

    private fun evaluateUnaryBoolean(
        state: State,
        expression: UnaryExpression
    ): Evaluation<Boolean> {
        val (state, value) = evaluateBoolean(state, expression.operand)
        return when (expression.operator) {
            UnaryOperator.NOT -> Evaluation.of(state, !value)
        }
    }

    private fun evaluateBinaryBoolean(
        state: State,
        expression: BinaryExpression
    ): Evaluation<Boolean> {
        return when (expression.operator) {
            BinaryOperator.LESS_THAN -> {
                val (state1, lhs) = evaluateInt(state, expression.lhs)
                val (state2, rhs) = evaluateInt(state1, expression.rhs)
                Evaluation.of(state2, lhs < rhs)
            }
            else ->
                throw NotImplementedError(
                    "evaluateBinaryBoolean of ${expression::class.simpleName}")
        }
    }

    private fun evaluateTermBoolean(
        state: State,
        expression: BooleanTermExpression
    ): Evaluation<Boolean> {
        return Evaluation.of(state, expression.value)
    }

    fun evaluateInt(state: State, expression: BinaryExpression): Evaluation<Int> {
        val (state1, lhs) = evaluateInt(state, expression.lhs)
        val (state2, rhs) = evaluateInt(state1, expression.rhs)
        return Evaluation.of(
            state2,
            when (expression.operator) {
                BinaryOperator.ADD -> lhs + rhs
                BinaryOperator.MINUS -> lhs - rhs
                BinaryOperator.MULTIPLY -> lhs * rhs
                BinaryOperator.DIVIDE -> lhs / rhs
                BinaryOperator.MODULO -> lhs % rhs
                else -> throw NotImplementedError("evaluateInt of ${expression::class.simpleName}")
            })
    }

    private fun evaluateInt(state: State, expression: IntTermExpression): Evaluation<Int> {
        return Evaluation.of(state, expression.value)
    }

    private fun evaluateInt(state: State, expression: VariableTermExpression): Evaluation<Int> {
        val value =
            state.callStack.peek()!!.read(expression.identifier)
                ?: throw UseOfUndeclaredVariableError(expression.identifier)
        return Evaluation.of(state, (value as IntTermExpression).value)
    }
}

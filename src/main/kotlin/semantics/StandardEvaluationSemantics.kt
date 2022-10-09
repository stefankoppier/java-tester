package semantics

import engine.EvaluationSemantics
import engine.state.State
import language.java.syntax.expressions.*
import semantics.errors.WriteToUndeclaredVariableError

class StandardEvaluationSemantics(val simplifier: ExpressionSimplifier) : EvaluationSemantics() {

    override fun evaluate(state: State, expression: Expression): Evaluation<Unit> {
        return when (expression) {
            is AssignmentExpression -> evaluateAssign(state, expression)
            else -> throw NotImplementedError("evaluate of ${expression::class.simpleName}") // Requires a type system
        }
    }

    private fun evaluateAssign(state: State, expression: AssignmentExpression): Evaluation<Unit> {
        val (state, value) = evaluateInt(state, expression.value)

        val stackFrame = state.callStack.peek()
        if (stackFrame!!.read(expression.lhs.name) == null) {
            throw WriteToUndeclaredVariableError(expression.lhs.name)
        }

        stackFrame.write(expression.lhs.name, IntTermExpression(value))
        return Evaluation.of(state, Unit)
    }

    override fun evaluateNullableInt(state: State, expression: Expression?): Evaluation<Int?> {
        return if (expression != null) Evaluation.nullable(evaluateInt(state, expression))
        else Evaluation.of(state, null)
    }

    override fun evaluateInt(state: State, expression: Expression): Evaluation<Int> {
        return when (val simplified = simplifier(expression)(state)) {
            is IntTermExpression -> Evaluation.of(state, simplified.value)
            else -> throw IllegalStateException("Failed to evaluate expression $simplified as an int")
        }
    }

    override fun evaluateBoolean(state: State, expression: Expression): Evaluation<Boolean> {
        return when (val simplified = simplifier(expression)(state)) {
            is BooleanTermExpression -> Evaluation.of(state, simplified.value)
            else -> throw IllegalStateException("Failed to evaluate expression $simplified as a boolean")
        }
    }
}

package engine

import engine.state.State
import language.java.syntax.expressions.Expression
import semantics.Evaluation

abstract class EvaluationSemantics() {

    abstract fun evaluate(state: State, expression: Expression): Evaluation<Unit>

    abstract fun evaluateNullableInt(state: State, expression: Expression?): Evaluation<Int?>

    abstract fun evaluateInt(state: State, expression: Expression): Evaluation<Int>

    abstract fun evaluateBoolean(state: State, expression: Expression): Evaluation<Boolean>
}

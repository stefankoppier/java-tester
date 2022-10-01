package semantics

import engine.state.State

data class Evaluation<T>(val state: State, val value: T) {
    companion object {
        fun <T> of(state: State, value: T): Evaluation<T> {
            return Evaluation(state, value)
        }

        fun <T> nullable(result: Evaluation<T>): Evaluation<T?> {
            return of(result.state, result.value)
        }
    }
}

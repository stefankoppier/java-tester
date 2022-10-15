package engine

import engine.state.State
import semantics.output.ResultGenerator
import semantics.output.TestCase

class Engine {
    operator fun invoke(executor: Executor): List<TestCase> {
        run(State.of(), executor)
        return ResultGenerator.results
    }

    private fun run(state: State, executor: Executor) {
        executor.execute(state).forEach { nextState ->
            executor.follow(nextState).forEach { nextExecutor -> run(nextState, nextExecutor) }
        }
    }
}

package semantics.execution.statements

import engine.ExecutionContext
import engine.Executor
import engine.state.State
import language.java.analysis.cfa.Labeled
import language.java.syntax.expressions.IntTermExpression
import language.java.syntax.statements.ReturnStatement

class ReturnStatementExecutor(val statement: Labeled<ReturnStatement>) : Executor {
    override fun execute(state: State): List<State> {
        val (state, value) = ExecutionContext.semantics.evaluateNullableInt(state, statement.content.value)
        state.callStack.peek()?.retval = value?.let(::IntTermExpression)
        return listOf(state)
    }

    override fun follow(state: State): List<Executor> {
        return follow(statement)
    }
}

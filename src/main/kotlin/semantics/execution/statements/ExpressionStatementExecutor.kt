package semantics.execution.statements

import engine.ExecutionContext
import engine.Executor
import engine.state.State
import language.java.analysis.cfa.Labeled
import language.java.syntax.statements.ExpressionStatement

class ExpressionStatementExecutor(val statement: Labeled<ExpressionStatement>) : Executor {
    override fun execute(state: State): List<State> {
        return listOf(ExecutionContext.semantics.evaluate(state, statement.content.expression).state)
    }

    override fun follow(state: State): List<Executor> {
        return follow(statement)
    }
}

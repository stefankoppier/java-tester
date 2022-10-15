package semantics.execution.statements

import engine.Executor
import engine.identity
import engine.state.State
import language.java.analysis.cfa.Labeled
import language.java.syntax.statements.Statement

class EmptyStatementExecutor(val statement: Labeled<Statement>) : Executor {
    override fun execute(state: State): List<State> {
        return identity(state)
    }

    override fun follow(state: State): List<Executor> {
        return follow(statement)
    }
}

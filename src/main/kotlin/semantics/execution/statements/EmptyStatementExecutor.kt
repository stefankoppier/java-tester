package semantics.execution.statements

import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.Labeled
import language.java.syntax.statements.Statement
import semantics.execution.Executor
import semantics.execution.identity

class EmptyStatementExecutor(val statement: Labeled<Statement>) : Executor {
    override fun execute(state: State): List<State> {
        return identity(state)
    }

    override fun follow(cfg: ControlFlowGraph): List<ControlFlowGraphEdge> {
        return cfg.neighbours(statement)
    }
}

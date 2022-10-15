package semantics.execution.statements

import engine.EvaluationSemantics
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.Labeled
import language.java.syntax.statements.ExpressionStatement
import semantics.execution.Executor

class ExpressionStatementExecutor(val statement: Labeled<ExpressionStatement>, val semantics: EvaluationSemantics) :
    Executor {
    override fun execute(state: State): List<State> {
        return listOf(semantics.evaluate(state, statement.content.expression).state)
    }

    override fun follow(cfg: ControlFlowGraph): List<ControlFlowGraphEdge> {
        return cfg.neighbours(statement)
    }
}

package semantics.execution.statements

import engine.EvaluationSemantics
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.Labeled
import language.java.syntax.expressions.IntTermExpression
import language.java.syntax.statements.ReturnStatement
import semantics.execution.Executor

class ReturnStatementExecutor(val statement: Labeled<ReturnStatement>, val semantics: EvaluationSemantics) : Executor {
    override fun execute(state: State): List<State> {
        val (state, value) = semantics.evaluateNullableInt(state, statement.content.value)
        state.callStack.peek()?.retval = value?.let(::IntTermExpression)
        return listOf(state)
    }

    override fun follow(cfg: ControlFlowGraph): List<ControlFlowGraphEdge> {
        return cfg.neighbours(statement)
    }
}

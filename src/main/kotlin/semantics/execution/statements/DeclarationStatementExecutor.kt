package semantics.execution.statements

import engine.EvaluationSemantics
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.Labeled
import language.java.syntax.expressions.IntTermExpression
import language.java.syntax.statements.DeclarationStatement
import semantics.execution.Executor
import semantics.execution.default

class DeclarationStatementExecutor(val statement: Labeled<DeclarationStatement>, val semantics: EvaluationSemantics) :
    Executor {
    override fun execute(state: State): List<State> {
        val (state, value) = semantics.evaluateInt(state, statement.content.value ?: default(statement.content.type))
        state.callStack.peek()?.write(statement.content.name, IntTermExpression(value))
        return listOf(state)
    }

    override fun follow(cfg: ControlFlowGraph): List<ControlFlowGraphEdge> {
        return cfg.neighbours(statement)
    }
}

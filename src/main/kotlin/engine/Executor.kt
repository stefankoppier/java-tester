package engine

import engine.state.State
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.cfa.Labeled
import language.java.syntax.expressions.Expression
import language.java.syntax.type.BooleanType
import language.java.syntax.type.IntType
import language.java.syntax.type.NonVoidType
import semantics.execution.NodeExecutor

interface Executor {
    companion object {
        fun of(edge: ControlFlowGraphEdge): Executor {
            val guard =
                when (edge) {
                    is ControlFlowGraphEdge.GuardedEdge -> edge.guard
                    is ControlFlowGraphEdge.NormalEdge -> Expression.of(true)
                }

            return NodeExecutor(edge.second, guard)
        }
    }

    fun execute(state: State): List<State>

    fun follow(state: State): List<Executor>

    fun follow(node: Labeled<*>): List<Executor> {
        return ExecutionContext.cfg.neighbours(node).map { of(it) }
    }

    fun follow(node: ControlFlowGraphNode): List<Executor> {
        return ExecutionContext.cfg.outgoingEdgesOf(node).map { of(it) }
    }
}

internal fun identity(state: State): List<State> = listOf(state)

// TODO: this needs a new location
internal fun default(type: NonVoidType): Expression {
    return when (type) {
        is IntType -> Expression.of(0)
        is BooleanType -> Expression.of(false)
    }
}

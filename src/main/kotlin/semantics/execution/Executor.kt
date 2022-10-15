package semantics.execution

import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.syntax.expressions.Expression
import language.java.syntax.type.BooleanType
import language.java.syntax.type.IntType
import language.java.syntax.type.NonVoidType

interface Executor {
    fun execute(state: State): List<State>

    fun follow(cfg: ControlFlowGraph): List<ControlFlowGraphEdge>
}

internal fun identity(state: State): List<State> = listOf(state)

// TODO: this needs a new location
internal fun default(type: NonVoidType): Expression {
    return when (type) {
        is IntType -> Expression.of(0)
        is BooleanType -> Expression.of(false)
    }
}

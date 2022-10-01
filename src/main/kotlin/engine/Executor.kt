package engine

import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode

class Executor<R>(private val cfg: ControlFlowGraph, private val semantics: ExecutionSemantics<R>) {

    fun start(): R {
        val initialState = State.of()
        val initialNode = cfg.vertexSet().first { node -> node.label == initialState.pc }

        execute(initialState, initialNode)
        return semantics.result()
    }

    private fun execute(state: State, node: ControlFlowGraphNode) {
        println("execute $node $state")
        semantics.execute(state, node).forEach { state1 ->
            semantics.next(cfg, node).forEach { edge ->
                when (edge) {
                    is ControlFlowGraphEdge.GuardedEdge -> {
                        val (state2, value) =
                            semantics.evaluationSemantics.evaluateBoolean(state1, edge.guard)
                        if (value) {
                            execute(state2, edge.second)
                        }
                    }
                    is ControlFlowGraphEdge.NormalEdge -> execute(state1, edge.second)
                }
            }
        }
    }
}

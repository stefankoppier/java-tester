package engine

import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode

abstract class ExecutionSemantics<R>(val evaluationSemantics: EvaluationSemantics) {
    abstract fun result(): R

    abstract fun execute(state: State, node: ControlFlowGraphNode): List<State>

    abstract fun next(cfg: ControlFlowGraph, node: ControlFlowGraphNode): Set<ControlFlowGraphEdge>
}

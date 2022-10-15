package engine

import engine.state.State
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode

abstract class ExecutionSemantics<R>(val evaluation: EvaluationSemantics) {
    abstract fun result(): R

    abstract fun execute(state: State, node: ControlFlowGraphNode): List<State>

    abstract fun next(state: State, node: ControlFlowGraphNode): List<ControlFlowGraphEdge>
}

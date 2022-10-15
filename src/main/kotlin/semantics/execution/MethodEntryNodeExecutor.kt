package semantics.execution

import engine.state.StackFrame
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.syntax.statements.*
import semantics.execution.statements.*

class MethodEntryNodeExecutor(val node: ControlFlowGraphNode.MethodEntryNode, val cfg: ControlFlowGraph) : Executor {
    override fun execute(state: State): List<State> {
        if (state.callee == null) {
            state.callStack.push(StackFrame.of(null, null))
            return listOf(state)
        }
        return cfg.neighbours(state.callee!!).map { node ->
            val state1 = state.clone()
            val lhs = ((node.first as ControlFlowGraphNode.StatementNode).statement.content as InvocationStatement).lhs
            state1.callStack.push(StackFrame.of(node.second.label, lhs))
            state1
        }
    }

    override fun follow(cfg: ControlFlowGraph): List<ControlFlowGraphEdge> {
        return cfg.neighbours(node.label)
    }
}

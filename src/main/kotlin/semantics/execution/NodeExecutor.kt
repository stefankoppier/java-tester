package semantics.execution

import engine.ExecutionContext
import engine.Executor
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.syntax.expressions.Expression

class NodeExecutor(val node: ControlFlowGraphNode, val guard: Expression) : Executor {

    override fun execute(state: State): List<State> {
        state.pc = node.label
        return executor().execute(state)
    }

    override fun follow(state: State): List<Executor> {
        if (ExecutionContext.semantics.evaluateBoolean(state, guard).value) {
            return executor().follow(state)
        }
        return emptyList()
    }

    private fun executor(): Executor {
        return when (node) {
            is ControlFlowGraphNode.StatementNode -> StatementNodeExecutor(node)
            is ControlFlowGraphNode.MethodEntryNode -> MethodEntryNodeExecutor(node, emptyMap(), null)
            is ControlFlowGraphNode.MethodExitNode -> MethodExitNodeExecutor(node)
        }
    }
}

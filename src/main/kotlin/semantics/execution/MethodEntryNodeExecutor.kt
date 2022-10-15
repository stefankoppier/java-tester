package semantics.execution

import engine.ExecutionContext
import engine.Executor
import engine.state.StackFrame
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.syntax.Identifier
import language.java.syntax.expressions.Expression
import language.java.syntax.statements.*

class MethodEntryNodeExecutor(
    val node: ControlFlowGraphNode.MethodEntryNode,
    val arguments: Map<Identifier, Expression>,
    val callee: Int?
) : Executor {
    override fun execute(state: State): List<State> {
        if (isInitialMethod()) {
            state.callStack.push(StackFrame.of(null, null))
            arguments.forEach { argument -> state.callStack.peek()!!.write(argument.key, argument.value) }
            return listOf(state)
        }
        return ExecutionContext.cfg.neighbours(callee!!).map { edge ->
            val state1 = state.clone()
            val lhs = ((edge.first as ControlFlowGraphNode.StatementNode).statement.content as InvocationStatement).lhs
            state1.callStack.push(StackFrame.of(edge.second.label, lhs))
            arguments.forEach { argument -> state1.callStack.peek()!!.write(argument.key, argument.value) }
            state1
        }
    }

    override fun follow(state: State): List<Executor> {
        return follow(node)
    }

    private fun isInitialMethod() = callee == null
}

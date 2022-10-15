package semantics.execution

import engine.ExecutionContext
import engine.Executor
import engine.state.StackFrame
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode
import semantics.output.ResultGenerator
import semantics.output.TestCase

class MethodExitNodeExecutor(val node: ControlFlowGraphNode.MethodExitNode) : Executor {
    companion object {
        private var previous: StackFrame? = null // TODO: this cannot be in the companion object, guarantee for errors.
    }

    override fun execute(state: State): List<State> {
        val frame = state.callStack.pop()
        if (state.callStack.size == 0 && frame?.retval != null) {
            ResultGenerator.append(TestCase.of(node.method.content, frame.retval!!))
        }
        if (frame!!.target != null) {
            state.callStack.peek()!!.write(frame.target!!, frame.retval!!)
        }
        previous = frame
        return listOf(state)
    }

    override fun follow(state: State): List<Executor> {
        val retpc = previous!!.retpc
        if (retpc != null && retpc != -1) {
            return listOf(ControlFlowGraphEdge.of(node, ExecutionContext.cfg.find(retpc)!!)).map { Executor.of(it) }
        }
        return ExecutionContext.cfg.neighbours(node.label).map { Executor.of(it) }
    }
}

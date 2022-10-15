package semantics

import engine.ExecutionSemantics
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.symbols.SymbolTable
import semantics.execution.Executor
import semantics.execution.MethodEntryNodeExecutor
import semantics.execution.MethodExitNodeExecutor
import semantics.execution.StatementNodeExecutor
import semantics.output.ResultGenerator
import semantics.output.TestCase

class StandardSemantics(val cfg: ControlFlowGraph, val symbols: SymbolTable) :
    ExecutionSemantics<List<TestCase>>(StandardEvaluationSemantics(ExpressionSimplifier())) {

    override fun result(): List<TestCase> {
        return ResultGenerator.results
    }

    override fun execute(state: State, node: ControlFlowGraphNode): List<State> {
        state.pc = node.label
        return executor(node).execute(state)
    }

    override fun next(state: State, node: ControlFlowGraphNode): List<ControlFlowGraphEdge> {
        return executor(node).follow(cfg)
    }

    private fun executor(node: ControlFlowGraphNode): Executor {
        return when (node) {
            is ControlFlowGraphNode.StatementNode -> StatementNodeExecutor(node, evaluation, symbols)
            is ControlFlowGraphNode.MethodEntryNode -> MethodEntryNodeExecutor(node, cfg)
            is ControlFlowGraphNode.MethodExitNode -> MethodExitNodeExecutor(node)
        }
    }
}

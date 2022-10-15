package engine

import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.symbols.SymbolTable
import semantics.errors.CannotFindSymbolError

class Executor<R>(
    private val cfg: ControlFlowGraph,
    private val symbols: SymbolTable,
    private val semantics: ExecutionSemantics<R>
) {

    fun start(): R {
        val initialState = State.of()
        val initialSymbol =
            symbols.lookup(ApplicationConfiguration.entry()!!)
                ?: throw CannotFindSymbolError(ApplicationConfiguration.entry()!!)

        execute(initialState, ControlFlowGraphNode.MethodEntryNode(initialSymbol.label, initialSymbol.method))
        return semantics.result()
    }

    private fun execute(initialState: State, node: ControlFlowGraphNode) {
        println("execute $node $initialState")
        semantics.execute(initialState, node).forEach { executedState ->
            semantics.next(executedState, node).forEach { edge ->
                when (edge) {
                    is ControlFlowGraphEdge.GuardedEdge -> {
                        val (evaluatedState, value) = semantics.evaluation.evaluateBoolean(executedState, edge.guard)
                        if (value) {
                            execute(evaluatedState, edge.second)
                        }
                    }
                    is ControlFlowGraphEdge.NormalEdge -> execute(executedState, edge.second)
                }
            }
        }
    }
}

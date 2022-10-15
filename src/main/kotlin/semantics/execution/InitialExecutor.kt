package semantics.execution

import engine.EvaluationSemantics
import engine.ExecutionContext
import engine.Executor
import engine.identity
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.symbols.SymbolTable
import semantics.errors.CannotFindSymbolError

class InitialExecutor(val cfg: ControlFlowGraph, val symbols: SymbolTable, val semantics: EvaluationSemantics) :
    Executor {
    override fun execute(state: State): List<State> {
        ExecutionContext.initialize(cfg, symbols, semantics)
        return identity(state)
    }

    override fun follow(state: State): List<Executor> {
        val initialSymbol =
            ExecutionContext.symbols.lookup(ApplicationConfiguration.entry()!!)
                ?: throw CannotFindSymbolError(ApplicationConfiguration.entry()!!)

        val node = ControlFlowGraphNode.MethodEntryNode(initialSymbol.label, initialSymbol.method)
        return listOf(MethodEntryNodeExecutor(node, emptyMap(), null))
    }
}

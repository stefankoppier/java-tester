package engine

import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.symbols.SymbolTable

class ExecutionContext {
    companion object {
        lateinit var cfg: ControlFlowGraph
            private set

        lateinit var symbols: SymbolTable
            private set

        lateinit var semantics: EvaluationSemantics
            private set

        fun initialize(cfg: ControlFlowGraph, symbols: SymbolTable, semantics: EvaluationSemantics) {
            this.cfg = cfg
            this.symbols = symbols
            this.semantics = semantics
        }
    }
}

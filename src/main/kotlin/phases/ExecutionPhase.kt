package phases

import engine.Executor
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.symbols.SymbolTable
import semantics.StandardSemantics
import semantics.output.TestGenerator

class ExecutionPhase(val cfg: ControlFlowGraph, val symbols: SymbolTable) {
    operator fun invoke() {
        val result = Executor(cfg, symbols, StandardSemantics(cfg, symbols)).start()
        println(result)
        println(TestGenerator(result).generate())
    }
}

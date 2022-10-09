package phases

import language.java.analysis.cfa.ClassDefinitionGraphConstructor
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.symbols.SymbolTable
import language.java.analysis.symbols.SymbolTableConstructor
import language.java.syntax.ClassDefinition

class AnalysisPhase(private val ast: ClassDefinition) {
    companion object {
        private const val LABEL = 0
    }

    operator fun invoke(): Pair<ControlFlowGraph, SymbolTable> {
        val cfg = ClassDefinitionGraphConstructor(LABEL, ast).construct(ControlFlowGraph())
        println(cfg)
        val symbols = SymbolTableConstructor().construct(cfg)
        println(symbols)
        return Pair(cfg, symbols)
    }
}

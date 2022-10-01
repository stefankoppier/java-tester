package phases

import language.java.analysis.cfa.ClassDefinitionGraphConstructor
import language.java.analysis.cfa.ControlFlowGraph
import language.java.syntax.ClassDefinition

class AnalysisPhase(private val ast: ClassDefinition) {
    companion object {
        private const val LABEL = 0
    }

    operator fun invoke(): ControlFlowGraph {
        val cfg = ClassDefinitionGraphConstructor(LABEL, ast).construct(ControlFlowGraph())
        println(cfg)
        return cfg
    }
}

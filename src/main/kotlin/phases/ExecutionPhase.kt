package phases

import engine.Executor
import language.java.analysis.cfa.ControlFlowGraph
import semantics.StandardSemantics
import semantics.output.TestGenerator

class ExecutionPhase(val cfg: ControlFlowGraph) {
    operator fun invoke() {
        val result = Executor(cfg, StandardSemantics()).start()
        println(result)
        println(TestGenerator(result).generate())
    }
}

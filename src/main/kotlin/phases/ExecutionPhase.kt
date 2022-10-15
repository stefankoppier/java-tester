package phases

import engine.Engine
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.symbols.SymbolTable
import semantics.ExpressionSimplifier
import semantics.StandardEvaluationSemantics
import semantics.execution.InitialExecutor
import semantics.output.TestGenerator

class ExecutionPhase(val cfg: ControlFlowGraph, val symbols: SymbolTable) {
    operator fun invoke() {
        val semantics = StandardEvaluationSemantics(ExpressionSimplifier())
        val executor = InitialExecutor(cfg, symbols, semantics)
        val result = Engine().invoke(executor)
        println(result)
        println(TestGenerator(result).generate())
    }
}

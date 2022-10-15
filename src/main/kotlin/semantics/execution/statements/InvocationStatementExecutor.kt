package semantics.execution.statements

import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.Labeled
import language.java.analysis.symbols.SymbolTable
import language.java.syntax.statements.InvocationStatement
import semantics.errors.CannotFindSymbolError
import semantics.execution.Executor
import semantics.execution.default

class InvocationStatementExecutor(val statement: Labeled<InvocationStatement>, val symbols: SymbolTable) : Executor {
    override fun execute(state: State): List<State> {
        state.callStack.peek()!!.write(statement.content.lhs, default(statement.content.type)) // TODO: retval
        state.callee = state.pc
        return listOf(state)
    }

    override fun follow(cfg: ControlFlowGraph): List<ControlFlowGraphEdge> {
        val name = statement.content.name
        val method = symbols.lookup(name) ?: throw CannotFindSymbolError(name)
        return listOf(ControlFlowGraphEdge.of(cfg.find(statement)!!, cfg.find(method.label)!!))
    }
}

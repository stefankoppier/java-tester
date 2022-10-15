package semantics.execution.statements

import engine.ExecutionContext
import engine.Executor
import engine.default
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.cfa.Labeled
import language.java.syntax.Identifier
import language.java.syntax.Parameter
import language.java.syntax.expressions.Expression
import language.java.syntax.statements.InvocationStatement
import semantics.errors.CannotFindSymbolError
import semantics.execution.MethodEntryNodeExecutor

class InvocationStatementExecutor(val statement: Labeled<InvocationStatement>) : Executor {
    override fun execute(state: State): List<State> {
        state.callStack.peek()!!.write(statement.content.lhs, default(statement.content.type)) // TODO: retval
        return listOf(state)
    }

    override fun follow(state: State): List<Executor> {
        val name = statement.content.name
        val method = ExecutionContext.symbols.lookup(name) ?: throw CannotFindSymbolError(name)
        val entryNode = (ExecutionContext.cfg.find(method.label) as ControlFlowGraphNode.MethodEntryNode)
        return listOf(MethodEntryNodeExecutor(entryNode, arguments(method.method.parameters), state.pc))
    }

    private fun arguments(parameters: List<Parameter>): Map<Identifier, Expression> {
        return parameters
            .zip(statement.content.arguments) { parameter, argument -> Pair(parameter.name, argument) }
            .toMap()
    }
}

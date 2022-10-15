package semantics.execution.statements

import engine.ExecutionContext
import engine.Executor
import engine.default
import engine.state.State
import language.java.analysis.cfa.Labeled
import language.java.syntax.expressions.IntTermExpression
import language.java.syntax.statements.DeclarationStatement

class DeclarationStatementExecutor(val statement: Labeled<DeclarationStatement>) : Executor {

    private val evaluation = ExecutionContext.semantics

    override fun execute(state: State): List<State> {
        val (state, value) = evaluation.evaluateInt(state, statement.content.value ?: default(statement.content.type))
        state.callStack.peek()?.write(statement.content.name, IntTermExpression(value))
        return listOf(state)
    }

    override fun follow(state: State): List<Executor> {
        return follow(statement)
    }
}

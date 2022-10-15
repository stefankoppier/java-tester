package semantics.execution

import engine.Executor
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.cfa.Labeled
import language.java.syntax.statements.*
import semantics.execution.statements.*

class StatementNodeExecutor(val node: ControlFlowGraphNode.StatementNode) : Executor {
    override fun execute(state: State): List<State> {
        return executor(node.statement).execute(state)
    }

    override fun follow(state: State): List<Executor> {
        return executor(node.statement).follow(state)
    }

    private fun executor(statement: Labeled<Statement>): Executor {
        return when (statement.content) {
            is DeclarationStatement -> DeclarationStatementExecutor(statement as Labeled<DeclarationStatement>)
            is ReturnStatement -> ReturnStatementExecutor(statement as Labeled<ReturnStatement>)
            is InvocationStatement -> InvocationStatementExecutor(statement as Labeled<InvocationStatement>)
            is BreakStatement -> EmptyStatementExecutor(statement)
            is ContinueStatement -> EmptyStatementExecutor(statement)
            is BlockStatement -> EmptyStatementExecutor(statement)
            is EmptyStatement -> EmptyStatementExecutor(statement)
            is IfThenElseStatement -> EmptyStatementExecutor(statement)
            is WhileStatement -> EmptyStatementExecutor(statement)
            is ExpressionStatement -> ExpressionStatementExecutor(statement as Labeled<ExpressionStatement>)
            else -> throw IllegalStateException("unable to find executor for ${statement::class.simpleName}")
        }
    }
}

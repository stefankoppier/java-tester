package semantics.execution

import engine.EvaluationSemantics
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.cfa.Labeled
import language.java.analysis.symbols.SymbolTable
import language.java.syntax.statements.*
import semantics.execution.statements.*

class StatementNodeExecutor(
    val node: ControlFlowGraphNode.StatementNode,
    val semantics: EvaluationSemantics,
    val symbols: SymbolTable
) : Executor {
    override fun execute(state: State): List<State> {
        return executor(node.statement).execute(state)
    }

    override fun follow(cfg: ControlFlowGraph): List<ControlFlowGraphEdge> {
        return executor(node.statement).follow(cfg)
    }

    private fun executor(statement: Labeled<Statement>): Executor {
        return when (statement.content) {
            is DeclarationStatement ->
                DeclarationStatementExecutor(statement as Labeled<DeclarationStatement>, semantics)
            is ReturnStatement -> ReturnStatementExecutor(statement as Labeled<ReturnStatement>, semantics)
            is InvocationStatement -> InvocationStatementExecutor(statement as Labeled<InvocationStatement>, symbols)
            is BreakStatement -> EmptyStatementExecutor(statement)
            is ContinueStatement -> EmptyStatementExecutor(statement)
            is BlockStatement -> EmptyStatementExecutor(statement)
            is EmptyStatement -> EmptyStatementExecutor(statement)
            is IfThenElseStatement -> EmptyStatementExecutor(statement)
            is WhileStatement -> EmptyStatementExecutor(statement)
            is ExpressionStatement -> ExpressionStatementExecutor(statement as Labeled<ExpressionStatement>, semantics)
            else -> throw IllegalStateException("unable to find executor for ${statement::class.simpleName}")
        }
    }
}

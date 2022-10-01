package semantics

import engine.ExecutionSemantics
import engine.state.StackFrame
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.syntax.MethodDeclaration
import language.java.syntax.expressions.BooleanTermExpression
import language.java.syntax.expressions.Expression
import language.java.syntax.expressions.IntTermExpression
import language.java.syntax.statements.*
import language.java.syntax.type.BooleanType
import language.java.syntax.type.IntType
import language.java.syntax.type.NonVoidType
import semantics.output.ResultGenerator
import semantics.output.TestCase

class StandardSemantics : ExecutionSemantics<List<TestCase>>(StandardEvaluationSemantics()) {

    override fun result(): List<TestCase> {
        return ResultGenerator.results
    }

    override fun execute(state: State, node: ControlFlowGraphNode): List<State> {
        return when (node) {
            is ControlFlowGraphNode.StatementNode -> executeStatement(state.clone(), node.statement)
            is ControlFlowGraphNode.MethodEntryNode ->
                executeMethodEntry(state.clone(), node.method)
            is ControlFlowGraphNode.MethodExitNode -> executeMethodExit(state.clone(), node.method)
        }
    }

    override fun next(
        cfg: ControlFlowGraph,
        node: ControlFlowGraphNode
    ): Set<ControlFlowGraphEdge> {
        return cfg.outgoingEdgesOf(node)
    }

    private fun executeMethodEntry(state: State, method: MethodDeclaration): List<State> {
        state.callStack.push(StackFrame.of())
        return listOf(state)
    }

    private fun executeMethodExit(state: State, method: MethodDeclaration): List<State> {
        val stackFrame = state.callStack.pop()
        if (stackFrame?.retval != null) {
            ResultGenerator.append(TestCase.of(method, stackFrame.retval!!))
        }

        return listOf(state)
    }

    private fun executeStatement(state: State, statement: Statement): List<State> {
        return when (statement) {
            is DeclarationStatement -> executeDeclarationStatement(state, statement)
            is ReturnStatement -> executeReturnStatement(state, statement)
            is BreakStatement -> listOf(state)
            is ContinueStatement -> listOf(state)
            is BlockStatement -> listOf(state)
            is EmptyStatement -> listOf(state)
            is IfThenElseStatement -> listOf(state)
            is WhileStatement -> listOf(state)
            is ExpressionStatement -> executeExpressionStatement(state, statement)
            else -> throw NotImplementedError("execute for ${statement::class.simpleName}")
        }
    }

    private fun executeDeclarationStatement(
        state: State,
        statement: DeclarationStatement
    ): List<State> {
        val (state, value) =
            evaluationSemantics.evaluateInt(state, statement.value ?: default(statement.type))
        state.callStack.peek()?.write(statement.identifier, IntTermExpression(value))
        return listOf(state)
    }

    private fun default(type: NonVoidType): Expression {
        return when (type) {
            is IntType -> IntTermExpression(0)
            is BooleanType -> BooleanTermExpression(false)
        }
    }

    private fun executeReturnStatement(state: State, statement: ReturnStatement): List<State> {
        val (state, value) = evaluationSemantics.evaluateNullableInt(state, statement.value)
        state.callStack.peek()?.retval = value?.let(::IntTermExpression)
        return listOf(state)
    }

    private fun executeExpressionStatement(
        state: State,
        statement: ExpressionStatement
    ): List<State> {
        val (state, _) = evaluationSemantics.evaluate(state, statement.expression)
        return listOf(state)
    }
}

package semantics

import engine.ExecutionSemantics
import engine.state.StackFrame
import engine.state.State
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.symbols.SymbolTable
import language.java.syntax.MethodDeclaration
import language.java.syntax.expressions.Expression
import language.java.syntax.expressions.IntTermExpression
import language.java.syntax.statements.*
import language.java.syntax.type.BooleanType
import language.java.syntax.type.IntType
import language.java.syntax.type.NonVoidType
import semantics.errors.CannotFindSymbolError
import semantics.output.ResultGenerator
import semantics.output.TestCase

class StandardSemantics(val cfg: ControlFlowGraph, val symbols: SymbolTable) :
    ExecutionSemantics<List<TestCase>>(StandardEvaluationSemantics(ExpressionSimplifier())) {

    override fun result(): List<TestCase> {
        return ResultGenerator.results
    }

    override fun execute(state: State, node: ControlFlowGraphNode): List<State> {
        state.pc = node.label
        return when (node) {
            is ControlFlowGraphNode.StatementNode -> executeStatement(state.clone(), node.statement)
            is ControlFlowGraphNode.MethodEntryNode -> executeMethodEntry(state.clone(), node.method)
            is ControlFlowGraphNode.MethodExitNode -> executeMethodExit(state.clone(), node.method)
        }
    }

    override fun next(state: State, node: ControlFlowGraphNode): Set<ControlFlowGraphEdge> {
        return when (node) {
            is ControlFlowGraphNode.StatementNode -> {
                when (node.statement) {
                    is InvocationStatement -> {
                        val name = node.statement.name
                        val label = symbols.lookup(name)?.label ?: throw CannotFindSymbolError(name)
                        val next = cfg.find(label)
                        setOf(ControlFlowGraphEdge.of(node, next!!))
                    }
                    else -> cfg.outgoingEdgesOf(node)
                }
            }
            is ControlFlowGraphNode.MethodExitNode -> {
                val frame = state.callStack.peek()
                val retpc = frame!!.retpc
                state.callStack.pop()
                if (retpc != null && retpc != -1) {
                    return setOf(ControlFlowGraphEdge.of(node, cfg.find(retpc)!!))
                }
                cfg.outgoingEdgesOf(node)
            }
            else -> cfg.outgoingEdgesOf(node)
        }
    }

    private fun executeMethodEntry(state: State, method: MethodDeclaration): List<State> {
        if (state.callee == null) {
            state.callStack.push(StackFrame.of(null, null))
            return listOf(state)
        }
        return cfg.neighbours(state.callee!!).map { node ->
            val state1 = state.clone()
            val lhs = ((node.first as ControlFlowGraphNode.StatementNode).statement as InvocationStatement).lhs
            state1.callStack.push(StackFrame.of(node.second.label, lhs))
            state1
        }
    }

    private fun executeMethodExit(state: State, method: MethodDeclaration): List<State> {
        val stackFrame = state.callStack.peek()
        if (state.callStack.size == 1 && stackFrame?.retval != null) {
            ResultGenerator.append(TestCase.of(method, stackFrame.retval!!))
        }

        if (stackFrame!!.target != null) {
            state.callStack.callee()!!.write(stackFrame.target!!, stackFrame.retval!!)
        }

        return listOf(state)
    }

    private fun executeStatement(state: State, statement: Statement): List<State> {
        return when (statement) {
            is DeclarationStatement -> executeDeclarationStatement(state, statement)
            is ReturnStatement -> executeReturnStatement(state, statement)
            is InvocationStatement -> executeInvocationStatement(state, statement)
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

    private fun executeInvocationStatement(state: State, statement: InvocationStatement): List<State> {
        state.callStack.peek()!!.write(statement.lhs, default(statement.type)) // TODO: retval
        state.callee = state.pc
        return listOf(state)
    }

    private fun executeDeclarationStatement(state: State, statement: DeclarationStatement): List<State> {
        val (state, value) = evaluation.evaluateInt(state, statement.value ?: default(statement.type))
        state.callStack.peek()?.write(statement.name, IntTermExpression(value))
        return listOf(state)
    }

    private fun default(type: NonVoidType): Expression {
        return when (type) {
            is IntType -> Expression.of(0)
            is BooleanType -> Expression.of(false)
        }
    }

    private fun executeReturnStatement(state: State, statement: ReturnStatement): List<State> {
        val (state, value) = evaluation.evaluateNullableInt(state, statement.value)
        state.callStack.peek()?.retval = value?.let(::IntTermExpression)
        return listOf(state)
    }

    private fun executeExpressionStatement(state: State, statement: ExpressionStatement): List<State> {
        val (state, _) = evaluation.evaluate(state, statement.expression)
        return listOf(state)
    }
}

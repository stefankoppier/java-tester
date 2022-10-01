package language.java.analysis.cfa.statements

import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.cfa.GraphConstructor
import language.java.syntax.statements.BreakStatement
import language.java.syntax.statements.ContinueStatement
import language.java.syntax.statements.ReturnStatement
import language.java.syntax.statements.WhileStatement

class WhileStatementGraphConstructor(label: Int, val statement: WhileStatement) :
    GraphConstructor(label) {

    private val node = ControlFlowGraphNode.StatementNode(current(), statement)

    private val body = of(current() + 1, statement.body)

    override fun init(): ControlFlowGraphNode {
        return node
    }

    override fun final(): Set<ControlFlowGraphNode> {
        return setOf(node) union (body.fallthrough().filter(this::isBreakStatement))
    }

    override fun fallthrough(): Set<ControlFlowGraphNode> {
        return body.fallthrough().filter(this::isReturnStatement).toSet()
    }

    override fun construct(cfg: ControlFlowGraph): ControlFlowGraph {
        return body.construct(cfg).insertNodes(setOf(node)).insertEdges(edges())
    }

    override fun next(): Int {
        return body.next()
    }

    private fun edges(): Set<ControlFlowGraphEdge> =
        setOf(ControlFlowGraphEdge.of(statement.guard, node, body.init()))
            .union(body.final().map { final -> ControlFlowGraphEdge.of(final, init()) })
            .union(
                body.fallthrough().filter(this::isContinueStatement).map { final ->
                    ControlFlowGraphEdge.of(final, body.init())
                })

    private fun isReturnStatement(node: ControlFlowGraphNode) =
        node is ControlFlowGraphNode.StatementNode && node.statement is ReturnStatement

    private fun isBreakStatement(node: ControlFlowGraphNode) =
        node is ControlFlowGraphNode.StatementNode && node.statement is BreakStatement

    private fun isContinueStatement(node: ControlFlowGraphNode) =
        node is ControlFlowGraphNode.StatementNode && node.statement is ContinueStatement
}

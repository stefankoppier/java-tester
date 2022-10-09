package language.java.analysis.cfa.statements

import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.cfa.GraphConstructor
import language.java.syntax.statements.IfThenElseStatement

class IfThenElseStatementGraphConstructor(label: Int, val statement: IfThenElseStatement) : GraphConstructor(label) {

    private val node = ControlFlowGraphNode.StatementNode(current(), statement)

    private val trueStatement = of(current() + 1, statement.trueStatement)

    private val falseStatement = ofNullable(trueStatement.next(), statement.falseStatement)

    override fun init(): ControlFlowGraphNode {
        return node
    }

    override fun final(): Set<ControlFlowGraphNode> {
        return trueStatement.final() union (falseStatement?.final() ?: emptySet())
    }

    override fun fallthrough(): Set<ControlFlowGraphNode> {
        return trueStatement.fallthrough() union (falseStatement?.fallthrough() ?: emptySet())
    }

    override fun construct(cfg: ControlFlowGraph): ControlFlowGraph {
        trueStatement.construct(cfg)
        falseStatement?.construct(cfg)

        val edges =
            setOf(ControlFlowGraphEdge.of(statement.guard, node, trueStatement.init()))
                .union(
                    if (falseStatement == null) emptySet()
                    else setOf(ControlFlowGraphEdge.of(!statement.guard, node, falseStatement.init()))
                )

        cfg.insertNodes(setOf(node)).insertEdges(edges)
        return cfg
    }

    override fun next(): Int {
        return falseStatement?.next() ?: trueStatement.next()
    }
}

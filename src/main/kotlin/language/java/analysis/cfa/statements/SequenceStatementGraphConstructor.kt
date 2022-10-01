package language.java.analysis.cfa.statements

import compose
import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphEdge
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.cfa.GraphConstructor
import language.java.syntax.expressions.UnaryExpression
import language.java.syntax.expressions.UnaryOperator
import language.java.syntax.statements.SequenceStatement
import language.java.syntax.statements.WhileStatement

class SequenceStatementGraphConstructor(label: Int, val statement: SequenceStatement) :
    GraphConstructor(label) {

    private val first = of(current(), statement.first)

    private val second = of(first.next(), statement.second)

    override fun init(): ControlFlowGraphNode {
        return first.init()
    }

    override fun final(): Set<ControlFlowGraphNode> {
        return first.fallthrough().ifEmpty { second.final() }
    }

    override fun fallthrough(): Set<ControlFlowGraphNode> {
        return first.fallthrough() union second.fallthrough()
    }

    override fun construct(cfg: ControlFlowGraph): ControlFlowGraph {
        return compose(second::construct, first::construct)(cfg).insertEdges(edges())
    }

    private fun edges() =
        first.final().map { final ->
            if ((final is ControlFlowGraphNode.StatementNode) &&
                (final.statement is WhileStatement)) {
                ControlFlowGraphEdge.of(
                    UnaryExpression(UnaryOperator.NOT, final.statement.guard), final, second.init())
            } else {
                ControlFlowGraphEdge.of(final, second.init())
            }
        }

    override fun next(): Int {
        return second.next()
    }
}

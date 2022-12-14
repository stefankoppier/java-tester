package language.java.analysis.cfa.statements

import compose
import language.java.analysis.cfa.*
import language.java.syntax.statements.SequenceStatement
import language.java.syntax.statements.WhileStatement

class SequenceStatementGraphConstructor(label: Int, val statement: SequenceStatement) : GraphConstructor(label) {

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
            if ((final is ControlFlowGraphNode.StatementNode) && (final.statement.content is WhileStatement)) {
                ControlFlowGraphEdge.of(!final.statement.content.guard, final, second.init())
            } else {
                ControlFlowGraphEdge.of(final, second.init())
            }
        }

    override fun next(): Int {
        return second.next()
    }
}

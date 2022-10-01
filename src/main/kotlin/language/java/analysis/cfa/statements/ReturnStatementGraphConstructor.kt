package language.java.analysis.cfa.statements

import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.cfa.GraphConstructor
import language.java.syntax.statements.ReturnStatement

class ReturnStatementGraphConstructor(label: Int, val statement: ReturnStatement) :
    GraphConstructor(label) {

    private val node = ControlFlowGraphNode.StatementNode(current(), statement)

    override fun init(): ControlFlowGraphNode {
        return node
    }

    override fun final(): Set<ControlFlowGraphNode> {
        return emptySet()
    }

    override fun fallthrough(): Set<ControlFlowGraphNode> {
        return setOf(node)
    }

    override fun construct(cfg: ControlFlowGraph): ControlFlowGraph {
        return cfg.insertNodes(setOf(node))
    }
}

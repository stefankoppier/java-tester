package language.java.analysis.cfa.statements

import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphNode
import language.java.analysis.cfa.GraphConstructor
import language.java.syntax.statements.BlockStatement

class BlockStatementGraphConstructor(label: Int, val statement: BlockStatement) : GraphConstructor(label) {

    private val body = of(current(), statement.body)

    override fun init(): ControlFlowGraphNode {
        return body.init()
    }

    override fun final(): Set<ControlFlowGraphNode> {
        return body.final()
    }

    override fun fallthrough(): Set<ControlFlowGraphNode> {
        return body.fallthrough()
    }

    override fun construct(cfg: ControlFlowGraph): ControlFlowGraph {
        return body.construct(cfg)
    }

    override fun next(): Int {
        return body.next()
    }
}

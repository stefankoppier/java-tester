package language.java.analysis.cfa

import language.java.syntax.MethodDeclaration

class MethodDeclarationGraphConstructor(label: Int, val method: MethodDeclaration) : GraphConstructor(label) {

    private val body = of(label + 1, method.body)

    private val entry = ControlFlowGraphNode.MethodEntryNode(label, method)

    private val exit = ControlFlowGraphNode.MethodExitNode(body.next(), method)

    override fun init(): ControlFlowGraphNode {
        return entry
    }

    override fun final(): Set<ControlFlowGraphNode> {
        return setOf(exit)
    }

    override fun fallthrough(): Set<ControlFlowGraphNode> {
        return emptySet()
    }

    override fun construct(cfg: ControlFlowGraph): ControlFlowGraph {
        return body.construct(cfg).insertNodes(setOf(entry) union (setOf(exit))).insertEdges(edges())
    }

    override fun next(): Int {
        return exit.label + 1
    }

    private fun edges() =
        setOf(ControlFlowGraphEdge.of(entry, body.init()))
            .union(body.final().map { final -> ControlFlowGraphEdge.of(final, exit) })
            .union(body.fallthrough().map { final -> ControlFlowGraphEdge.of(final, exit) })
}

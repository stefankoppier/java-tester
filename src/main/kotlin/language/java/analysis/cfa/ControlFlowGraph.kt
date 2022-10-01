package language.java.analysis.cfa

import java.util.*
import language.java.syntax.MethodDeclaration
import language.java.syntax.expressions.Expression
import language.java.syntax.statements.Statement
import org.jgrapht.graph.DefaultDirectedGraph
import org.jgrapht.graph.DefaultEdge

class ControlFlowGraph : DefaultDirectedGraph<ControlFlowGraphNode, ControlFlowGraphEdge>(clazz) {

    companion object {
        private val clazz = ControlFlowGraphEdge::class.java
    }

    fun insertNodes(nodes: Set<ControlFlowGraphNode>): ControlFlowGraph {
        return nodes.fold(this) { cfg, node -> cfg.addNode(node) }
    }

    fun addNode(node: ControlFlowGraphNode): ControlFlowGraph {
        addVertex(node)
        return this
    }

    fun insertEdges(edges: Iterable<ControlFlowGraphEdge>): ControlFlowGraph {
        return edges.fold(this) { cfg, edge -> cfg.insertEdge(edge) }
    }

    fun insertEdge(edge: ControlFlowGraphEdge): ControlFlowGraph {
        addEdge(edge.first, edge.second, edge)
        return this
    }
}

sealed class ControlFlowGraphEdge(
    val first: ControlFlowGraphNode,
    val second: ControlFlowGraphNode
) : DefaultEdge() {

    companion object {
        fun of(first: ControlFlowGraphNode, second: ControlFlowGraphNode): ControlFlowGraphEdge {
            return NormalEdge(first, second)
        }

        fun of(
            guard: Expression,
            first: ControlFlowGraphNode,
            second: ControlFlowGraphNode
        ): ControlFlowGraphEdge {
            return GuardedEdge(guard, first, second)
        }
    }

    class NormalEdge(first: ControlFlowGraphNode, second: ControlFlowGraphNode) :
        ControlFlowGraphEdge(first, second)

    class GuardedEdge(
        val guard: Expression,
        first: ControlFlowGraphNode,
        second: ControlFlowGraphNode
    ) : ControlFlowGraphEdge(first, second)

    override fun equals(other: Any?): Boolean {
        return if (other is ControlFlowGraphEdge) first == other.first && second == other.second
        else false
    }

    override fun hashCode(): Int {
        return Objects.hash(first, second)
    }
}

sealed class ControlFlowGraphNode(val label: Int) {
    override fun equals(other: Any?): Boolean {
        return if (other is ControlFlowGraphNode) label == other.label else false
    }

    override fun hashCode(): Int {
        return label.hashCode()
    }

    class StatementNode(label: Int, val statement: Statement) : ControlFlowGraphNode(label) {
        override fun toString(): String {
            return "StatementNode{$label $statement}"
        }
    }

    class MethodEntryNode(label: Int, val method: MethodDeclaration) : ControlFlowGraphNode(label) {
        override fun toString(): String {
            return "MethodEntryNode{$label $method}"
        }
    }

    class MethodExitNode(label: Int, val method: MethodDeclaration) : ControlFlowGraphNode(label) {
        override fun toString(): String {
            return "MethodExitNode{$label $method}"
        }
    }
}

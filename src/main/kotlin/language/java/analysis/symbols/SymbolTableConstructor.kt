package language.java.analysis.symbols

import language.java.analysis.cfa.ControlFlowGraph
import language.java.analysis.cfa.ControlFlowGraphNode

class SymbolTableConstructor {

    fun construct(cfg: ControlFlowGraph): SymbolTable {
        val symbols = SymbolTable.of()
        cfg.vertexSet()
            .filter { node -> node is ControlFlowGraphNode.MethodEntryNode }
            .map { node -> (node as ControlFlowGraphNode.MethodEntryNode) }
            .forEach { node ->
                symbols.insert(node.method.content.name, SymbolTableEntry(node.label, node.method.content))
            }
        return symbols
    }
}

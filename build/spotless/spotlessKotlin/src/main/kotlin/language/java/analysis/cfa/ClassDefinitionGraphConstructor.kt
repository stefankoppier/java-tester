package language.java.analysis.cfa

import language.java.syntax.ClassDefinition

class ClassDefinitionGraphConstructor(label: Int, val ast: ClassDefinition) :
    GraphConstructor(label) {

    override fun init(): ControlFlowGraphNode {
        TODO("Not yet implemented")
    }

    override fun final(): Set<ControlFlowGraphNode> {
        TODO("Not yet implemented")
    }

    override fun fallthrough(): Set<ControlFlowGraphNode> {
        TODO("Not yet implemented")
    }

    override fun construct(cfg: ControlFlowGraph): ControlFlowGraph {
        var previous = MethodDeclarationGraphConstructor(current(), ast.members.first())
        previous.construct(cfg)
        for (member in ast.members.drop(1)) {
            val current = MethodDeclarationGraphConstructor(previous.next(), member)
            current.construct(cfg)
            previous = current
        }
        return cfg
    }
}

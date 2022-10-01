package language.java.analysis.cfa

import language.java.analysis.cfa.statements.*
import language.java.syntax.statements.*

abstract class GraphConstructor(val label: Int) {
    companion object {
        fun of(label: Int, statement: Statement): GraphConstructor {
            return when (statement) {
                is SequenceStatement -> SequenceStatementGraphConstructor(label, statement)
                is WhileStatement -> WhileStatementGraphConstructor(label, statement)
                is IfThenElseStatement -> IfThenElseStatementGraphConstructor(label, statement)
                is ReturnStatement -> ReturnStatementGraphConstructor(label, statement)
                is BreakStatement -> BreakStatementGraphConstructor(label, statement)
                is ContinueStatement -> ContinueStatementGraphConstructor(label, statement)
                is BlockStatement -> BlockStatementGraphConstructor(label, statement)
                else -> SimpleStatementGraphConstructor(label, statement)
            }
        }

        fun ofNullable(label: Int, statement: Statement?): GraphConstructor? {
            return if (statement == null) null else of(label, statement)
        }
    }

    abstract fun init(): ControlFlowGraphNode

    abstract fun final(): Set<ControlFlowGraphNode>

    abstract fun fallthrough(): Set<ControlFlowGraphNode>

    abstract fun construct(cfg: ControlFlowGraph): ControlFlowGraph

    fun current(): Int {
        return label
    }

    open fun next(): Int {
        return label + 1
    }
}

package engine.state

import language.java.syntax.Identifier
import language.java.syntax.expressions.Expression

class StackFrame(
    var retval: Expression? = null,
    var retpc: Int?,
    var target: Identifier?,
    private val variables: MutableMap<Identifier, Expression> = mutableMapOf()
) {

    companion object {
        fun of(retpc: Int?, target: Identifier?): StackFrame {
            return StackFrame(retpc = retpc, target = target)
        }
    }

    override fun toString(): String {
        return "StackFrame{$retval $retpc $target $variables}"
    }

    fun write(identifier: Identifier, expression: Expression) {
        variables[identifier] = expression
    }

    fun read(identifier: Identifier): Expression? {
        return variables[identifier]
    }

    fun copy(): StackFrame {
        return StackFrame(
            retval,
            retpc,
            target,
            mutableMapOf(*(variables.map { variable -> Pair(variable.key, variable.value) }.toTypedArray()))
        )
    }
}

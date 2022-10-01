package engine.state

import language.java.syntax.Identifier
import language.java.syntax.expressions.Expression

class StackFrame(
    var retval: Expression? = null,
    private val variables: MutableMap<Identifier, Expression> = mutableMapOf()
) {

    companion object {
        fun of(): StackFrame {
            return StackFrame()
        }
    }

    override fun toString(): String {
        return "StackFrame{$retval $variables}"
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
            mutableMapOf(
                *(variables.map { variable -> Pair(variable.key, variable.value) }.toTypedArray())))
    }
}

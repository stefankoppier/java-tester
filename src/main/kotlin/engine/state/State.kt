package engine.state

class State(var pc: Int, val callStack: CallStack, var callee: Int?) {
    companion object {
        fun of(): State {
            return State(0, CallStack.of(), null)
        }
    }

    override fun toString(): String {
        return "State{$pc $callStack $callee}"
    }

    fun clone(): State {
        return State(pc, callStack.clone(), callee)
    }
}

package engine.state

class State(val pc: Int, val callStack: CallStack) {
    companion object {
        fun of(): State {
            return State(0, CallStack.of())
        }
    }

    override fun toString(): String {
        return "State{$pc $callStack}"
    }

    fun clone(): State {
        return State(pc, callStack.clone())
    }
}

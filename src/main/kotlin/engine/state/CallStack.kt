package engine.state

class CallStack(private val frames: ArrayDeque<StackFrame>) {
    companion object {
        fun of(): CallStack {
            return CallStack(ArrayDeque(20))
        }
    }

    val size
        get() = frames.size

    override fun toString(): String {
        return "CallStack{$frames}"
    }

    fun push(frame: StackFrame) {
        return frames.addLast(frame)
    }

    fun pop(): StackFrame? {
        return frames.removeLastOrNull()
    }

    fun peek(): StackFrame? {
        return frames.lastOrNull()
    }

    fun callee(): StackFrame? {
        return if (frames.size > 1) frames[frames.size - 2] else null
    }

    fun clone(): CallStack {
        return CallStack(ArrayDeque(frames.map { frame -> frame.copy() }))
    }
}

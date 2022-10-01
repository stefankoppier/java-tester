package language.java.lexing

import org.typemeta.funcj.parser.Input

class TokenInput(var input: List<Token>) : Input<Token> {

    private var position: Int = 0

    override fun isEof(): Boolean {
        return position >= input.size
    }

    override fun get(): Token {
        return input[position]
    }

    override fun next(): Input<Token> {
        position++
        return this
    }

    override fun position(): Any {
        return input[position].position
    }

    override fun toString(): String {
        val dataStr = if (isEof) "EOF" else position().toString()
        return "TokenInput{$position, data=\"$dataStr\""
    }
}

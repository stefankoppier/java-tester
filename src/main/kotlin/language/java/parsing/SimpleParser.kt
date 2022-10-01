package language.java.parsing

import language.java.lexing.*
import org.typemeta.funcj.data.Lazy
import org.typemeta.funcj.parser.Combinators.value
import org.typemeta.funcj.parser.Input
import org.typemeta.funcj.parser.Parser
import org.typemeta.funcj.parser.Result
import org.typemeta.funcj.parser.SymSet

interface SimpleTokenParser<A> : Parser<Token, A> {
    override fun acceptsEmpty(): Lazy<Boolean> {
        return parser().acceptsEmpty()
    }

    override fun firstSet(): Lazy<SymSet<Token>> {
        return parser().firstSet()
    }

    override fun apply(input: Input<Token>?, follow: SymSet<Token>?): Result<Token, A> {
        return parser().apply(input)
    }

    fun parser(): Parser<Token, A>
}

fun token(token: Token): Parser<Token, Token> {
    return value(token)
}

fun semicolon(): Parser<Token, Unit> {
    return token(SemicolonToken()).map {}
}

fun comma(): Parser<Token, Unit> {
    return token(CommaToken()).map {}
}

fun <A> Parser<Token, A>.betweenCurly(): Parser<Token, A> {
    return this.between(token(CurlyOpenToken()), token(CurlyCloseToken()))
}

fun <A> Parser<Token, A>.betweenRound(): Parser<Token, A> {
    return this.between(value(RoundOpenToken()), value(RoundCloseToken()))
}

package language.java.parsing.expressions

import language.java.lexing.*
import language.java.parsing.SimpleTokenParser
import language.java.syntax.expressions.BooleanTermExpression
import org.typemeta.funcj.parser.*
import org.typemeta.funcj.parser.Combinators.choice
import org.typemeta.funcj.parser.Combinators.satisfy

class BooleanTermParser : SimpleTokenParser<BooleanTermExpression> {

    private val name = this.javaClass.name

    override fun parser(): Parser<Token, BooleanTermExpression> {
        return choice(
            satisfy<Token>(name) { token -> token is TrueToken }
                .map { BooleanTermExpression(true) },
            satisfy<Token>(name) { token -> token is FalseToken }
                .map { BooleanTermExpression(false) },
        )
    }
}

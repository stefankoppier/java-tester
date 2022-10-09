package language.java.parsing.expressions

import language.java.lexing.*
import language.java.parsing.SimpleTokenParser
import language.java.syntax.expressions.Expression
import language.java.syntax.expressions.IntTermExpression
import org.typemeta.funcj.parser.*
import org.typemeta.funcj.parser.Combinators.satisfy

class IntTermParser : SimpleTokenParser<IntTermExpression> {

    private val name = this.javaClass.name

    override fun parser(): Parser<Token, IntTermExpression> {
        return satisfy<Token>(name) { token -> token is NumberToken }
            .map { x -> Expression.of((x as NumberToken).value.toInt()) }
    }
}

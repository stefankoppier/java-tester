package language.java.parsing

import language.java.lexing.IdentifierToken
import language.java.lexing.Token
import language.java.syntax.Identifier
import org.typemeta.funcj.parser.*

class IdentifierParser : SimpleTokenParser<Identifier> {

    private val name = this.javaClass.name

    override fun parser(): Parser<Token, Identifier> {
        return Combinators.satisfy<Token>(name) { token -> token is IdentifierToken }
            .map { x -> Identifier((x as IdentifierToken).value, x.position) }
    }
}

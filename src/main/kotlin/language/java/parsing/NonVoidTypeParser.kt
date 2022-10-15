package language.java.parsing

import language.java.lexing.BooleanToken
import language.java.lexing.IntToken
import language.java.lexing.Token
import language.java.syntax.type.BooleanType
import language.java.syntax.type.IntType
import language.java.syntax.type.NonVoidType
import org.typemeta.funcj.parser.Combinators.choice
import org.typemeta.funcj.parser.Parser

class NonVoidTypeParser : SimpleTokenParser<NonVoidType> {

    override fun parser(): Parser<Token, NonVoidType> {
        return choice(
            token(IntToken()).map { IntType(it.position) },
            token(BooleanToken()).map { BooleanType(it.position) },
        )
    }
}

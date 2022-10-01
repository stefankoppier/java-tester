package language.java.parsing

import language.java.lexing.Token
import language.java.lexing.VoidToken
import language.java.syntax.type.Type
import language.java.syntax.type.VoidType
import org.typemeta.funcj.parser.Combinators.choice
import org.typemeta.funcj.parser.Parser

class TypeParser : SimpleTokenParser<Type> {

    override fun parser(): Parser<Token, Type> {
        return choice(NonVoidTypeParser(), token(VoidToken()).map { VoidType() })
    }
}

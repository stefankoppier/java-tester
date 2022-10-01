package language.java.parsing.members

import language.java.lexing.Token
import language.java.parsing.IdentifierParser
import language.java.parsing.NonVoidTypeParser
import language.java.parsing.SimpleTokenParser
import language.java.syntax.Parameter
import org.typemeta.funcj.parser.Parser

class ParameterParser : SimpleTokenParser<Parameter> {

    override fun parser(): Parser<Token, Parameter> {
        return NonVoidTypeParser().and(IdentifierParser()).map { type, name ->
            Parameter(type, name)
        }
    }
}

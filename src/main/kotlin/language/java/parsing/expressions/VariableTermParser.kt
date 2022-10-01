package language.java.parsing.expressions

import language.java.lexing.*
import language.java.parsing.IdentifierParser
import language.java.parsing.SimpleTokenParser
import language.java.syntax.expressions.VariableTermExpression
import org.typemeta.funcj.parser.*

class VariableTermParser : SimpleTokenParser<VariableTermExpression> {

    override fun parser(): Parser<Token, VariableTermExpression> {
        return IdentifierParser().map { identifier -> VariableTermExpression(identifier) }
    }
}

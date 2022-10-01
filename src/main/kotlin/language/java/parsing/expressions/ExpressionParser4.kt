package language.java.parsing.expressions

import language.java.lexing.*
import language.java.parsing.SimpleTokenParser
import language.java.syntax.expressions.Expression
import org.typemeta.funcj.parser.Parser

class ExpressionParser4 : SimpleTokenParser<Expression> {
    override fun parser(): Parser<Token, Expression> {
        return ExpressionParser5()
    }
}

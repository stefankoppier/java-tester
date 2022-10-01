package language.java.parsing

import language.java.lexing.Token
import language.java.parsing.expressions.ExpressionParser1
import language.java.syntax.expressions.Expression
import org.typemeta.funcj.parser.Parser

class ExpressionParser : SimpleTokenParser<Expression> {
    override fun parser(): Parser<Token, Expression> = ExpressionParser1()
}

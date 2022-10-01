package language.java.parsing.expressions

import language.java.lexing.Token
import language.java.parsing.SimpleTokenParser
import language.java.syntax.expressions.Expression
import org.typemeta.funcj.parser.Combinators.choice
import org.typemeta.funcj.parser.Parser

class ExpressionParser15 : SimpleTokenParser<Expression> {
    override fun parser(): Parser<Token, Expression> {
        return choice(
            IntTermParser(),
            BooleanTermParser(),
            VariableTermParser(),
            //            ExpressionParser().betweenRound(),
            )
    }
}

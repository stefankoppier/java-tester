package language.java.parsing.expressions

import language.java.lexing.NotToken
import language.java.lexing.Token
import language.java.parsing.SimpleTokenParser
import language.java.parsing.chainrUnary
import language.java.parsing.token
import language.java.syntax.expressions.Expression
import org.typemeta.funcj.functions.Functions
import org.typemeta.funcj.parser.Combinators.choice
import org.typemeta.funcj.parser.Parser

class ExpressionParser13 : SimpleTokenParser<Expression> {

    override fun parser(): Parser<Token, Expression> {
        return ExpressionParser14().chainrUnary(operators())
    }

    private fun operators(): Parser<Token, Functions.Op<Expression>> {
        return choice(token(NotToken()).map { Functions.Op.of { operand -> operand.not() } })
    }
}

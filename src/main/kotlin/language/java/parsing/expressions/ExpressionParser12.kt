package language.java.parsing.expressions

import language.java.lexing.DivideToken
import language.java.lexing.ModuloToken
import language.java.lexing.TimesToken
import language.java.lexing.Token
import language.java.parsing.SimpleTokenParser
import language.java.parsing.token
import language.java.syntax.expressions.BinaryExpression
import language.java.syntax.expressions.BinaryOperator
import language.java.syntax.expressions.Expression
import org.typemeta.funcj.functions.Functions
import org.typemeta.funcj.parser.*
import org.typemeta.funcj.parser.Combinators.choice

class ExpressionParser12 : SimpleTokenParser<Expression> {

    override fun parser(): Parser<Token, Expression> {
        return ExpressionParser13().chainl1(operators())
    }

    private fun operators(): Parser<Token, Functions.Op2<Expression>> {
        return choice(
            token(TimesToken()).map {
                Functions.Op2.of { lhs, rhs -> BinaryExpression(lhs, BinaryOperator.MULTIPLY, rhs) }
            },
            token(DivideToken()).map {
                Functions.Op2.of { lhs, rhs -> BinaryExpression(lhs, BinaryOperator.DIVIDE, rhs) }
            },
            token(ModuloToken()).map {
                Functions.Op2.of { lhs, rhs -> BinaryExpression(lhs, BinaryOperator.MODULO, rhs) }
            },
        )
    }
}

package language.java.parsing.expressions

import language.java.lexing.*
import language.java.parsing.SimpleTokenParser
import language.java.parsing.token
import language.java.syntax.expressions.BinaryExpression
import language.java.syntax.expressions.Expression
import org.typemeta.funcj.functions.Functions
import org.typemeta.funcj.parser.Combinators.choice
import org.typemeta.funcj.parser.Parser

class ExpressionParser9 : SimpleTokenParser<Expression> {
    override fun parser(): Parser<Token, Expression> {
        return ExpressionParser10().chainl1(operators())
    }

    private fun operators(): Parser<Token, Functions.Op2<Expression>> {
        return choice(
            token(LessThanToken()).map {
                Functions.Op2.of { lhs, rhs -> BinaryExpression(lhs, BinaryExpression.Operator.LESS_THAN, rhs) }
            },
            token(LessThanEqualsToken()).map {
                Functions.Op2.of { lhs, rhs -> BinaryExpression(lhs, BinaryExpression.Operator.LESS_THAN_EQUALS, rhs) }
            },
            token(GreaterThanToken()).map {
                Functions.Op2.of { lhs, rhs -> BinaryExpression(lhs, BinaryExpression.Operator.GREATER_THAN, rhs) }
            },
            token(GreaterThanEqualsToken()).map {
                Functions.Op2.of { lhs, rhs ->
                    BinaryExpression(lhs, BinaryExpression.Operator.GREATER_THAN_EQUALS, rhs)
                }
            },
        )
    }
}

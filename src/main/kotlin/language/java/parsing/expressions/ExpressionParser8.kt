package language.java.parsing.expressions

import language.java.lexing.*
import language.java.parsing.SimpleTokenParser
import language.java.parsing.token
import language.java.syntax.expressions.BinaryExpression
import language.java.syntax.expressions.Expression
import org.typemeta.funcj.functions.Functions
import org.typemeta.funcj.parser.Combinators.choice
import org.typemeta.funcj.parser.Parser

class ExpressionParser8 : SimpleTokenParser<Expression> {
    override fun parser(): Parser<Token, Expression> {
        return ExpressionParser9().chainl1(operators())
    }

    private fun operators(): Parser<Token, Functions.Op2<Expression>> {
        return choice(
            token(EqualsToken()).map {
                Functions.Op2.of { lhs, rhs -> BinaryExpression(lhs, BinaryExpression.Operator.EQUALS, rhs) }
            },
            token(NotEqualsToken()).map {
                Functions.Op2.of { lhs, rhs -> BinaryExpression(lhs, BinaryExpression.Operator.NOT_EQUALS, rhs) }
            },
        )
    }
}

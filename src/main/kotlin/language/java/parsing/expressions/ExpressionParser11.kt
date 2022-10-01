package language.java.parsing.expressions

import language.java.lexing.*
import language.java.parsing.SimpleTokenParser
import language.java.parsing.token
import language.java.syntax.expressions.BinaryExpression
import language.java.syntax.expressions.BinaryOperator
import language.java.syntax.expressions.Expression
import org.typemeta.funcj.functions.Functions
import org.typemeta.funcj.parser.Combinators.choice
import org.typemeta.funcj.parser.Parser

class ExpressionParser11 : SimpleTokenParser<Expression> {
    override fun parser(): Parser<Token, Expression> {
        return ExpressionParser12().chainl1(operators())
    }

    private fun operators(): Parser<Token, Functions.Op2<Expression>> {
        return choice(
            token(PlusToken()).map {
                Functions.Op2.of { lhs, rhs -> BinaryExpression(lhs, BinaryOperator.ADD, rhs) }
            },
            token(MinusToken()).map {
                Functions.Op2.of { lhs, rhs -> BinaryExpression(lhs, BinaryOperator.MINUS, rhs) }
            },
        )
    }
}

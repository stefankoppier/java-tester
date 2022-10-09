package language.java.parsing.expressions

import language.java.lexing.*
import language.java.parsing.SimpleTokenParser
import language.java.parsing.token
import language.java.syntax.expressions.*
import org.typemeta.funcj.functions.Functions
import org.typemeta.funcj.parser.*
import org.typemeta.funcj.parser.Combinators.choice

class ExpressionParser1 : SimpleTokenParser<Expression> {

    override fun parser(): Parser<Token, Expression> {
        return ExpressionParser2().chainr1(operators())
    }

    private fun operators(): Parser<Token, Functions.Op2<Expression>> {
        return choice(
            token(AssignmentToken()).map {
                Functions.Op2.of { lhs, rhs ->
                    when (lhs) {
                        is VariableTermExpression ->
                            AssignmentExpression(
                                VariableAssignmentTarget(lhs.name),
                                AssignmentExpression.Operator.ASSIGN,
                                rhs
                            )
                        else -> throw IllegalStateException("lhs must be a variable") // TODO(implement neatly)
                    }
                }
            }
        )
    }
}

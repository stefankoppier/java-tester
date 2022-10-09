package language.java.parsing.expressions

import language.java.lexing.*
import language.java.parsing.*
import language.java.syntax.Identifier
import language.java.syntax.expressions.Expression
import language.java.syntax.expressions.InvocationExpression
import language.java.syntax.expressions.VariableTermExpression
import org.typemeta.funcj.data.Lazy
import org.typemeta.funcj.parser.Combinators.choice
import org.typemeta.funcj.parser.Parser
import org.typemeta.funcj.parser.Parser.pure
import org.typemeta.funcj.parser.SymSet

class ExpressionParser15 : SimpleTokenParser<Expression> {

    override fun acceptsEmpty(): Lazy<Boolean> {
        return Lazy.of { false }
    }

    override fun firstSet(): Lazy<SymSet<Token>> {
        return Lazy.of {
            SymSet.value<Token>(IdentifierToken(""))
                .union(IntTermParser().firstSet().apply())
                .union(BooleanTermParser().firstSet().apply())
        }
    }

    override fun parser(): Parser<Token, Expression> {
        return choice(
            IntTermParser(),
            BooleanTermParser(),
            identifiable(),
        )
    }

    private fun identifiable(): Parser<Token, Expression> {
        return IdentifierParser().and(choice(InvocationTermParser(), VariableTermParser())).map {
            identifier,
            identifiable ->
            identifiable(identifier)
        }
    }

    private class VariableTermParser : SimpleTokenParser<(Identifier) -> VariableTermExpression> {

        //        override fun acceptsEmpty(): Lazy<Boolean> {
        //            return Lazy.of { true }
        //        }
        //
        //        override fun firstSet(): Lazy<SymSet<Token>> {
        //            return Lazy.of { SymSet.all() }
        //        }

        override fun parser(): Parser<Token, (Identifier) -> VariableTermExpression> {
            return pure<Token, Unit>(Unit).map { { identifier -> VariableTermExpression(identifier) } }
        }
    }

    private class InvocationTermParser : SimpleTokenParser<(Identifier) -> InvocationExpression> {

        //        override fun acceptsEmpty(): Lazy<Boolean> {
        //            return Lazy.of { false }
        //        }
        //
        //        override fun firstSet(): Lazy<SymSet<Token>> {
        //            return Lazy.of { SymSet.value(RoundOpenToken()) }
        //        }

        override fun parser(): Parser<Token, (Identifier) -> InvocationExpression> {
            return token(RoundOpenToken()).andR(ExpressionParser().sepBy(comma())).andL(token(RoundCloseToken())).map {
                arguments ->
                { identifier -> InvocationExpression(identifier, arguments.toList()) }
            }
        }
    }
}

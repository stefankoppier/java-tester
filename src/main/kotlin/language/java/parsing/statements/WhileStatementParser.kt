package language.java.parsing.statements

import language.java.lexing.*
import language.java.parsing.*
import language.java.syntax.statements.Statement
import language.java.syntax.statements.WhileStatement
import org.typemeta.funcj.data.Lazy
import org.typemeta.funcj.parser.*

class WhileStatementParser : SimpleTokenParser<Statement> {

    override fun acceptsEmpty(): Lazy<Boolean> {
        return Lazy.of { false }
    }

    override fun firstSet(): Lazy<SymSet<Token>> {
        return Lazy.of { SymSet.value(WhileToken()) }
    }

    override fun parser(): Parser<Token, Statement> {
        return token(WhileToken()).andR(ExpressionParser().betweenRound()).and(StatementParser()).map { guard, statement
            ->
            WhileStatement(guard, statement)
        }
    }
}

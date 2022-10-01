package language.java.parsing.statements

import kotlin.jvm.optionals.getOrNull
import language.java.lexing.*
import language.java.parsing.*
import language.java.syntax.statements.IfThenElseStatement
import language.java.syntax.statements.Statement
import org.typemeta.funcj.data.Lazy
import org.typemeta.funcj.parser.*

class IfThenElseStatementParser : SimpleTokenParser<Statement> {
    override fun acceptsEmpty(): Lazy<Boolean> {
        return Lazy.of { false }
    }

    override fun firstSet(): Lazy<SymSet<Token>> {
        return Lazy.of { SymSet.value(IfToken()) }
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun parser(): Parser<Token, Statement> {
        return token(IfToken())
            .andR(ExpressionParser().betweenRound())
            .and(StatementParser())
            .and(token(ElseToken()).optional().andR((StatementParser()).optional()))
            .map { guard, trueStatement, falseStatement ->
                IfThenElseStatement(guard, trueStatement, falseStatement.getOrNull())
            }
    }
}

package language.java.parsing

import language.java.lexing.Position.Companion.unknown
import language.java.lexing.Token
import language.java.parsing.statements.*
import language.java.syntax.statements.*
import org.typemeta.funcj.functions.Functions
import org.typemeta.funcj.parser.*
import org.typemeta.funcj.parser.Combinators.*

class StatementsParser : SimpleTokenParser<Statement> {

    override fun parser(): Parser<Token, Statement> {
        return StatementParser().chainr(sequence(), EmptyStatement(unknown()))
    }

    private fun sequence(): Parser<Token, Functions.Op2<Statement>> =
        Parser.pure<Token, Statement>(EmptyStatement(unknown())).map {
            Functions.Op2.of { first, second -> SequenceStatement(first, second, first.position) }
        }
}

class StatementParser : SimpleTokenParser<Statement> {

    override fun parser(): Parser<Token, Statement> {
        return choice(
            EmptyStatementParser(),
            ReturnStatementParser(),
            BreakStatementParser(),
            ContinueStatementParser(),
            IfThenElseStatementParser(),
            DeclarationStatementParser(),
            WhileStatementParser(),
            BlockStatementParser(),
            ExpressionStatementParser(),
        )
    }
}

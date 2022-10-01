package language.java.parsing.statements

import language.java.lexing.BreakToken
import language.java.lexing.Token
import language.java.parsing.SimpleTokenParser
import language.java.parsing.semicolon
import language.java.parsing.token
import language.java.syntax.statements.BreakStatement
import language.java.syntax.statements.Statement
import org.typemeta.funcj.parser.Parser

class BreakStatementParser : SimpleTokenParser<Statement> {

    override fun parser(): Parser<Token, Statement> {
        return token(BreakToken()).andL(semicolon()).map { BreakStatement() }
    }
}

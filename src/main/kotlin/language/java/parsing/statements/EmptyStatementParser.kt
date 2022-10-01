package language.java.parsing.statements

import language.java.lexing.Token
import language.java.parsing.SimpleTokenParser
import language.java.parsing.semicolon
import language.java.syntax.statements.EmptyStatement
import language.java.syntax.statements.Statement
import org.typemeta.funcj.parser.Parser

class EmptyStatementParser : SimpleTokenParser<Statement> {
    override fun parser(): Parser<Token, Statement> {
        return semicolon().map { EmptyStatement() }
    }
}

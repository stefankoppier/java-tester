package language.java.parsing.statements

import language.java.lexing.*
import language.java.parsing.SimpleTokenParser
import language.java.parsing.semicolon
import language.java.parsing.token
import language.java.syntax.statements.ContinueStatement
import language.java.syntax.statements.Statement
import org.typemeta.funcj.parser.Parser

class ContinueStatementParser : SimpleTokenParser<Statement> {

    override fun parser(): Parser<Token, Statement> {
        return token(ContinueToken()).andL(semicolon()).map { ContinueStatement() }
    }
}

package language.java.parsing.statements

import language.java.lexing.*
import language.java.parsing.ExpressionParser
import language.java.parsing.SimpleTokenParser
import language.java.parsing.semicolon
import language.java.syntax.statements.ExpressionStatement
import language.java.syntax.statements.Statement
import org.typemeta.funcj.parser.Parser

class ExpressionStatementParser : SimpleTokenParser<Statement> {

    override fun parser(): Parser<Token, Statement> {
        return ExpressionParser().andL(semicolon()).map { expression -> ExpressionStatement(expression) }
    }
}

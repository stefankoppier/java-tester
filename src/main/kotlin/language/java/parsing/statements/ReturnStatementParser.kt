package language.java.parsing.statements

import kotlin.jvm.optionals.getOrNull
import language.java.lexing.ReturnToken
import language.java.lexing.Token
import language.java.parsing.ExpressionParser
import language.java.parsing.SimpleTokenParser
import language.java.parsing.semicolon
import language.java.parsing.token
import language.java.syntax.statements.ReturnStatement
import language.java.syntax.statements.Statement
import org.typemeta.funcj.parser.Combinators.value
import org.typemeta.funcj.parser.Parser

class ReturnStatementParser : SimpleTokenParser<Statement> {

    @OptIn(ExperimentalStdlibApi::class)
    override fun parser(): Parser<Token, Statement> {
        return token(ReturnToken()).andR(ExpressionParser().optional()).andL(semicolon()).map { value ->
            ReturnStatement(value.getOrNull())
        }
    }
}

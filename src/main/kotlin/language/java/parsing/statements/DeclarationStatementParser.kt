package language.java.parsing.statements

import kotlin.jvm.optionals.getOrNull
import language.java.lexing.*
import language.java.parsing.*
import language.java.syntax.statements.DeclarationStatement
import language.java.syntax.statements.Statement
import org.typemeta.funcj.parser.*
import org.typemeta.funcj.parser.Combinators.value

class DeclarationStatementParser : SimpleTokenParser<Statement> {

    @OptIn(ExperimentalStdlibApi::class)
    override fun parser(): Parser<Token, Statement> {
        return NonVoidTypeParser()
            .and(IdentifierParser())
            .and(token(AssignmentToken()).andR(ExpressionParser()).optional())
            .andL(semicolon())
            .map { type, identifier, value -> DeclarationStatement(type, identifier, value.getOrNull()) }
    }
}

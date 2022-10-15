package language.java.parsing.statements

import language.java.lexing.*
import language.java.parsing.*
import language.java.syntax.statements.BlockStatement
import language.java.syntax.statements.Statement
import org.typemeta.funcj.data.Lazy
import org.typemeta.funcj.parser.*

class BlockStatementParser : SimpleTokenParser<Statement> {

    override fun acceptsEmpty(): Lazy<Boolean> {
        return Lazy.of { false }
    }

    override fun firstSet(): Lazy<SymSet<Token>> {
        return Lazy.of { SymSet.value(CurlyOpenToken()) }
    }

    override fun parser(): Parser<Token, Statement> {
        return StatementsParser().betweenCurly().map { statement -> BlockStatement(statement, statement.position) }
    }
}

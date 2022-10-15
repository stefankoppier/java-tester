package language.java.parsing.statements

import language.java.lexing.*
import language.java.parsing.ExpressionParser
import language.java.parsing.semicolon
import language.java.syntax.statements.ExpressionStatement
import language.java.syntax.statements.Statement
import org.typemeta.funcj.data.Lazy
import org.typemeta.funcj.parser.Input
import org.typemeta.funcj.parser.Parser
import org.typemeta.funcj.parser.Result
import org.typemeta.funcj.parser.SymSet

class ExpressionStatementParser : Parser<Token, Statement> {

    override fun acceptsEmpty(): Lazy<Boolean> {
        return Lazy.of { false }
    }

    override fun firstSet(): Lazy<SymSet<Token>> {
        return ExpressionParser().firstSet()
    }

    override fun apply(input: Input<Token>, follow: SymSet<Token>): Result<Token, Statement> {
        val position = input.position() as Position
        return ExpressionParser().andL(semicolon()).map { ExpressionStatement(it, position) as Statement }.apply(input)
    }
}

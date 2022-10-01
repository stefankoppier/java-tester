package phases

import java.io.Reader
import language.java.lexing.Lexer
import language.java.lexing.TokenInput
import language.java.parsing.ClassDefinitionParser
import language.java.syntax.ClassDefinition
import org.typemeta.funcj.parser.Combinators

class ParsingPhase(val input: Reader) {
    operator fun invoke(): ClassDefinition {
        val tokens = Lexer(input).lex()
        println(tokens)
        val ast = ClassDefinitionParser().andL(Combinators.eof()).apply(TokenInput(tokens))
        println(ast)
        return ast.orThrow
    }
}

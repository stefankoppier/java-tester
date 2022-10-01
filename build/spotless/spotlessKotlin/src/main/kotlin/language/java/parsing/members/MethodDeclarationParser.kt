package language.java.parsing.members

import language.java.lexing.*
import language.java.parsing.*
import language.java.syntax.MethodDeclaration
import language.java.syntax.Modifier
import org.typemeta.funcj.data.IList
import org.typemeta.funcj.parser.Combinators.choice
import org.typemeta.funcj.parser.Parser

class MethodDeclarationParser : SimpleTokenParser<MethodDeclaration> {
    override fun parser(): Parser<Token, MethodDeclaration> {
        return modifiers()
            .and(TypeParser())
            .and(IdentifierParser())
            .and(((ParameterParser().sepBy(comma()))).betweenRound())
            .and(StatementsParser().betweenCurly())
            .map { modifiers, type, name, parameters, body ->
                MethodDeclaration(modifiers.toList(), type, name, parameters.toList(), body)
            }
    }

    private fun modifiers(): Parser<Token, IList<Modifier>> {
        val options = arrayOf(token(StaticToken()).map { Modifier.STATIC })
        return modifiers(IList.of(), options)
    }

    // TODO: allows duplicates
    private fun modifiers(
        acc: IList<Modifier>,
        remaining: Array<Parser<Token, Modifier>>
    ): Parser<Token, IList<Modifier>> {
        return choice(*remaining).many()
    }
}

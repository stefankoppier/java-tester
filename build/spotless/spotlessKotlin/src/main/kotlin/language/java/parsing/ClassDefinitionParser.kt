package language.java.parsing

import language.java.lexing.ClassToken
import language.java.lexing.Token
import language.java.parsing.members.MethodDeclarationParser
import language.java.syntax.ClassDefinition
import org.typemeta.funcj.parser.Parser

class ClassDefinitionParser : SimpleTokenParser<ClassDefinition> {

    override fun parser(): Parser<Token, ClassDefinition> {
        return token(ClassToken())
            .andR(IdentifierParser())
            .and(MethodDeclarationParser().many().betweenCurly())
            .map { name, members -> ClassDefinition(name, members.toList()) }
    }
}

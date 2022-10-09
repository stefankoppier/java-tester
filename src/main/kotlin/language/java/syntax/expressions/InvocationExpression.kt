package language.java.syntax.expressions

import language.java.syntax.Identifier

class InvocationExpression(val name: Identifier, val arguments: List<Expression>) : Expression()

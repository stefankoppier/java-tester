package language.java.syntax.expressions

class AssignmentExpression(
    val lhs: VariableTermExpression,
    val operator: AssignmentOperator,
    val value: Expression
) : Expression()

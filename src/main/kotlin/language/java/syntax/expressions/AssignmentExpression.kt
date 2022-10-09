package language.java.syntax.expressions

class AssignmentExpression(val lhs: VariableAssignmentTarget, val operator: Operator, val value: Expression) :
    Expression() {

    enum class Operator {
        ASSIGN,
    }
}

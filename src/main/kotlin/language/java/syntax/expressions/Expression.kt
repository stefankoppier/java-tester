package language.java.syntax.expressions

sealed class Expression {

    companion object {
        fun of(value: Int): IntTermExpression {
            return IntTermExpression(value)
        }

        fun of(value: Boolean): BooleanTermExpression {
            return BooleanTermExpression(value)
        }
    }

    open fun isTerm(): Boolean = false

    open infix operator fun plus(that: Expression): Expression {
        return BinaryExpression(this, BinaryExpression.Operator.PLUS, that)
    }

    open infix operator fun minus(that: Expression): Expression {
        return BinaryExpression(this, BinaryExpression.Operator.MINUS, that)
    }

    open infix operator fun times(that: Expression): Expression {
        return BinaryExpression(this, BinaryExpression.Operator.TIMES, that)
    }

    open infix operator fun div(that: Expression): Expression {
        return BinaryExpression(this, BinaryExpression.Operator.DIVIDE, that)
    }

    open infix operator fun rem(that: Expression): Expression {
        return BinaryExpression(this, BinaryExpression.Operator.MODULO, that)
    }

    open operator fun not(): Expression {
        return UnaryExpression(UnaryExpression.Operator.NOT, this)
    }

    open infix fun equals(that: Expression): Expression {
        return BinaryExpression(this, BinaryExpression.Operator.EQUALS, that)
    }

    open infix fun lessthan(that: Expression): Expression {
        return BinaryExpression(this, BinaryExpression.Operator.LESS_THAN, that)
    }

    open infix fun greaterthan(that: Expression): Expression {
        return BinaryExpression(this, BinaryExpression.Operator.GREATER_THAN, that)
    }

    open infix fun and(that: Expression): Expression {
        return BinaryExpression(this, BinaryExpression.Operator.AND, that)
    }

    open infix fun or(that: Expression): Expression {
        return BinaryExpression(this, BinaryExpression.Operator.OR, that)
    }
}

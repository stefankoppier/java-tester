package language.java.syntax.expressions

class BooleanTermExpression internal constructor(val value: Boolean) : Expression() {

    override fun toString(): String {
        return "BooleanTermExpression{$value}"
    }

    override fun isTerm(): Boolean = true

    override operator fun not(): BooleanTermExpression {
        return of(!value)
    }

    override fun equals(that: Expression): Expression {
        return if (that.isTerm()) of(this.value == (that as BooleanTermExpression).value) else super.equals(that)
    }

    override fun and(that: Expression): Expression {
        return if (!value) return of(false) else that
    }

    override fun or(that: Expression): Expression {
        return if (value) return of(true) else that
    }
}

package language.java.syntax.expressions

class IntTermExpression internal constructor(val value: Int) : Expression() {

    override fun toString(): String {
        return "IntTermExpression{$value}"
    }

    override fun isTerm(): Boolean {
        return true
    }

    override fun equals(that: Expression): Expression {
        return if (that.isTerm()) of(this.value == (that as IntTermExpression).value) else super.equals(that)
    }

    override operator fun plus(that: Expression): Expression {
        return if (that.isTerm()) of(this.value + (that as IntTermExpression).value) else super.plus(that)
    }

    override operator fun minus(that: Expression): Expression {
        return if (that.isTerm()) of(this.value - (that as IntTermExpression).value) else super.plus(that)
    }

    override operator fun times(that: Expression): Expression {
        return if (that.isTerm()) of(this.value * (that as IntTermExpression).value) else super.plus(that)
    }

    override operator fun div(that: Expression): Expression {
        return if (that.isTerm()) of(this.value / (that as IntTermExpression).value) else super.plus(that)
    }

    override operator fun rem(that: Expression): Expression {
        return if (that.isTerm()) of(this.value % (that as IntTermExpression).value) else super.plus(that)
    }

    override fun lessthan(that: Expression): Expression {
        return if (that.isTerm()) of(this.value < (that as IntTermExpression).value) else super.plus(that)
    }

    override fun greaterthan(that: Expression): Expression {
        return if (that.isTerm()) of(this.value > (that as IntTermExpression).value) else super.plus(that)
    }
}

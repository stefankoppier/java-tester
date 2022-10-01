package semantics.output

import language.java.syntax.MethodDeclaration
import language.java.syntax.expressions.Expression

class TestCase(val method: MethodDeclaration, val expected: Expression) {

    companion object {
        fun of(method: MethodDeclaration, expected: Expression): TestCase {
            return TestCase(method, expected)
        }
    }

    override fun toString(): String {
        return "TestCase{$method $expected}"
    }
}

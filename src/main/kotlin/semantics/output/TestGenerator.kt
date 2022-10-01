package semantics.output

import language.java.syntax.expressions.IntTermExpression

class TestGenerator(val cases: List<TestCase>) {

    fun generate(): List<String> {
        return cases.map { generate(it) }
    }

    private fun generate(case: TestCase): String {
        val expected = (case.expected as IntTermExpression).value
        val methodName = case.method.name.identifier
        return """
            |@Test
            |void assert${methodName.replaceFirstChar { c -> c.uppercaseChar()}}Is${expected}() {
            |   assertEquals(${methodName}(), ${expected});
            |}
        """.trimMargin()
    }
}

package semantics.output

object ResultGenerator {

    val results = mutableListOf<TestCase>()

    fun append(case: TestCase) {
        results.add(case)
    }
}

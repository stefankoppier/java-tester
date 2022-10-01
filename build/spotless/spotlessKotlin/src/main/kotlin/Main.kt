import java.io.StringReader
import phases.AnalysisPhase
import phases.ExecutionPhase
import phases.ParsingPhase

const val input = """
class Application {
    static int main() {
        return 1;
    }
}
"""

fun main(args: Array<String>) {
    val ast = ParsingPhase(StringReader(input))()
    val cfg = AnalysisPhase(ast)()
    ExecutionPhase(cfg)()
}

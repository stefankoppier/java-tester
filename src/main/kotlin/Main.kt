import java.io.StringReader
import phases.AnalysisPhase
import phases.ExecutionPhase
import phases.ParsingPhase

const val input =
    """
class Application {
    static int f() {
        if (2 + 1 <= 3) {
            return 1 + 1 - 1 - 1;        
        } else {
            return f();
        }
    }
}
"""

fun main(args: Array<String>) {
    ApplicationConfiguration.entry("f")

    val ast = ParsingPhase(StringReader(input))()
    val (cfg, symbols) = AnalysisPhase(ast)()
    ExecutionPhase(cfg, symbols)()
}

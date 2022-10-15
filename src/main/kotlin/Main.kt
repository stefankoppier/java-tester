import java.io.StringReader
import phases.AnalysisPhase
import phases.ExecutionPhase
import phases.ParsingPhase

const val input =
    """
class Application {
    static int f() {
        if (1 <= 2) {
            return g(1);
        }
        return 0;
    }
   
    static int g(int x) {
        return x;
    }
}
"""

fun main(args: Array<String>) {
    ApplicationConfiguration.entry("f")

    val ast = ParsingPhase(StringReader(input))()
    val (cfg, symbols) = AnalysisPhase(ast)()
    ExecutionPhase(cfg, symbols)()
}

import org.example.analyzer.Analyzer;
import org.example.computer.Computer;
import org.example.parser.Parser;
import org.example.scanner.Scanner;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAnalyzer {

    private Analyzer getAnalyzer(String input) {
        return new Analyzer(new Parser(new Scanner(input), Logger.getLogger("parserLogger")).parse(), Logger.getLogger("analyzerLogger"));
    }

    @Test
    public void testSingleOp() {
        assertEquals(1, getAnalyzer("1 + 2").computeMaxCognitiveComplexity());
    }

    @Test
    public void testLineOfOp() {
        assertEquals(5, getAnalyzer("1 + 2 + 3 + 4 + 5 + 6").computeMaxCognitiveComplexity());
    }

    @Test
    public void testBalancedTree() {
        assertEquals(3, getAnalyzer("((1 + 2) * (3 + 4)) + ((5 - 6) / (7 - 8))").computeMaxCognitiveComplexity());
    }
}

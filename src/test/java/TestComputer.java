import org.example.computer.Computer;
import org.example.parser.Parser;
import org.example.scanner.Scanner;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestComputer {
    private Computer getComputer(String input) {
        return new Computer(new Parser(new Scanner(input), Logger.getLogger("parserLogger")).parse(), Logger.getLogger("ComputerLogger"));
    }

    @Test
    public void testSimple() {
        String input = "(1 + 2)";
        Computer computer = getComputer(input);
        assertEquals(3, computer.compute());
    }

    @Test
    public void testComplex() {
        String input = "(((1 +2) * (3/ 4 ))^(1/2) - (10 + 1 - 5 *(-7)))";

        Computer computer = getComputer(input);
        assertEquals(-44.5d, computer.compute());
    }

    @Test
    public void testFactorial() {
        String input = "1 * 2 * 3 * 4 * 5 * 6";

        Computer computer = getComputer(input);
        assertEquals(720, computer.compute());
    }

    @Test
    public void testBrackets() {
        String input = "(((((1)+(2))+(3))+(4))*(5))";

        Computer computer = getComputer(input);
        assertEquals(50, computer.compute());
    }

    @Test
    public void testZero() {
        String input = "0*0";

        Computer computer = getComputer(input);
        assertEquals(0, computer.compute());
    }

    @Test
    public void testLadder() {
        String input = "2^2^2^2";

        Computer computer = getComputer(input);
        assertEquals(1024 * 64, computer.compute());
    }
}

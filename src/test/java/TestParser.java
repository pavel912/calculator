import org.example.parser.*;
import org.example.scanner.OperationType;
import org.example.scanner.Scanner;
import org.example.scanner.TokenType;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class TestParser {
    private BinaryOpNode getBinOpNode(Node node) {
        assertEquals(NodeType.BINARY_OP, node.getType());
        assertInstanceOf(BinaryOpNode.class, node);
        return (BinaryOpNode) node;
    }

    private NumberNode getNumberNode(Node node) {
        assertEquals(NodeType.NUMBER, node.getType());
        assertInstanceOf(NumberNode.class, node);
        return (NumberNode) node;
    }

    @Test
    public void testSimple() {
        String input = "(1 + 2)";
        Parser parser = new Parser(new Scanner(input), Logger.getLogger("logger"));
        Node main = parser.parse();

        BinaryOpNode binaryOpNode = getBinOpNode(main);
        assertEquals(OperationType.ADD, binaryOpNode.getOperator());

        NumberNode left = getNumberNode(binaryOpNode.getLeft());
        NumberNode right = getNumberNode(binaryOpNode.getRight());

        assertEquals(1, left.getValue());
        assertEquals(2, right.getValue());
    }

    @Test
    public void testComplex() {
        String input = "(((1 +2) * (3/ 4 ))^(1/2) - (10 + 1 - 5 *(-7)))";

        Parser parser = new Parser(new Scanner(input), Logger.getLogger("logger"));
        Node main = parser.parse();
    }

    @Test
    public void testNegativeBracket() {
        String input = "(1 + 2";

        Parser parser = new Parser(new Scanner(input), Logger.getLogger("logger"));
        try {
            parser.parse();
        } catch (RuntimeException e) {
            assertEquals("End of input", e.getMessage());
        }
    }

    @Test
    public void testNegativeOperation() {
        String input = "1 ++ 2";

        Parser parser = new Parser(new Scanner(input), Logger.getLogger("logger"));
        try {
            parser.parse();
        } catch (RuntimeException e) {
            assertEquals("Invalid token. Expected: NUM or (expr), Got: OP", e.getMessage());
        }
    }
}

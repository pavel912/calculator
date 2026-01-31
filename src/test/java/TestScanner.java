import org.example.scanner.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestScanner {
    private Scanner scanner;

    private void assertNumerical(double val) {
        assertTrue(scanner.hasNext());
        Token token = scanner.next();
        assertEquals(TokenType.NUM, token.getTokenType());
        assertInstanceOf(NumberToken.class, token);
        NumberToken numberToken = (NumberToken) token;
        assertEquals(val, numberToken.getToken());
    }

    private void assertOperation(OperationType opType) {
        assertTrue(scanner.hasNext());
        Token token = scanner.next();
        assertEquals(TokenType.OP, token.getTokenType());
        assertInstanceOf(OperationToken.class, token);
        OperationToken opToken = (OperationToken) token;
        assertEquals(opType, opToken.getOperationType());
    }

    private void assertBracket(BracketType brType) {
        assertTrue(scanner.hasNext());
        Token token = scanner.next();
        assertEquals(TokenType.BRACKET, token.getTokenType());
        assertInstanceOf(BracketToken.class, token);
        BracketToken opToken = (BracketToken) token;
        assertEquals(brType, opToken.getBracketType());
    }

    private void assertUndef() {
        assertTrue(scanner.hasNext());
        Token token = scanner.next();
        assertEquals(TokenType.UNDEF, token.getTokenType());
        assertInstanceOf(UndefToken.class, token);
    }

    @Test
    public void testSimple() {
        String input = "1 + 2";
        this.scanner = new Scanner(input);

        assertNumerical(1);
        assertOperation(OperationType.ADD);
        assertNumerical(2);
        assertFalse(scanner.hasNext());
    }

    @Test
    public void testComplex() {
        String input = "(((1 +2) * (3/ 4 ))^(1/2) - (10 + 1 - 5 *(-7)))";
        this.scanner = new Scanner(input);

        assertBracket(BracketType.OPEN);
        assertBracket(BracketType.OPEN);
        assertBracket(BracketType.OPEN);

        assertNumerical(1);
        assertOperation(OperationType.ADD);
        assertNumerical(2);
        assertBracket(BracketType.CLOSE);

        assertOperation(OperationType.MULT);

        assertBracket(BracketType.OPEN);
        assertNumerical(3);
        assertOperation(OperationType.DIV);
        assertNumerical(4);
        assertBracket(BracketType.CLOSE);
        assertBracket(BracketType.CLOSE);

        assertOperation(OperationType.POW);

        assertBracket(BracketType.OPEN);
        assertNumerical(1);
        assertOperation(OperationType.DIV);
        assertNumerical(2);
        assertBracket(BracketType.CLOSE);

        assertOperation(OperationType.SUB);

        assertBracket(BracketType.OPEN);
        assertNumerical(10);
        assertOperation(OperationType.ADD);
        assertNumerical(1);
        assertOperation(OperationType.SUB);
        assertNumerical(5);
        assertOperation(OperationType.MULT);
        assertBracket(BracketType.OPEN);
        assertOperation(OperationType.SUB);
        assertNumerical(7);
        assertBracket(BracketType.CLOSE);
        assertBracket(BracketType.CLOSE);
        assertBracket(BracketType.CLOSE);
        assertFalse(scanner.hasNext());
    }

    @Test
    public void testIllegal() {
        String input = "(1.22p)";
        this.scanner = new Scanner(input);

        assertBracket(BracketType.OPEN);
        assertNumerical(1.22d);
        assertUndef();
        assertBracket(BracketType.CLOSE);
        assertFalse(scanner.hasNext());
    }

    @Test
    public void testFloatingPointNumbers() {
        this.scanner = new Scanner("1.0");
        assertNumerical(1.0d);

        this.scanner = new Scanner(".1");
        assertNumerical(0.1d);

        this.scanner = new Scanner("1.");
        assertNumerical(1.0d);

        assertThrows(NumberFormatException.class, () -> new Scanner("."));

        assertThrows(NumberFormatException.class, () -> new Scanner(".1. * 0.1.2"));
    }
}

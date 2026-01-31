package org.example.scanner;

public class BracketToken implements Token {
    private final TokenType type;
    private final BracketType bracketType;

    public BracketToken(char token) {
        this.type = TokenType.BRACKET;
        this.bracketType = getBracketType(token);
    }

    private BracketType getBracketType(char token) {
        return switch (token) {
            case '(' -> BracketType.OPEN;
            case ')' -> BracketType.CLOSE;
            default -> BracketType.UNDEF;
        };
    }

    public BracketType getBracketType() {
        return bracketType;
    }

    @Override
    public TokenType getTokenType() {
        return type;
    }
}

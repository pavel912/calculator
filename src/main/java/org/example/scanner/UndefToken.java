package org.example.scanner;

public class UndefToken implements Token {
    private final char token;
    private final TokenType type;

    public UndefToken(char token) {
        this.token = token;
        this.type = TokenType.UNDEF;
    }

    public char getToken() {
        return token;
    }

    @Override
    public TokenType getTokenType() {
        return type;
    }
}

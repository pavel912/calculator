package org.example.scanner;

public class NumberToken implements Token {
    private final double token;
    private final TokenType type;

    public NumberToken(double token) {
        this.token = token;
        this.type = TokenType.NUM;
    }

    public double getToken() {
        return token;
    }

    @Override
    public TokenType getTokenType() {
        return type;
    }
}

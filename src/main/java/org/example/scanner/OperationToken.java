package org.example.scanner;

public class OperationToken implements Token {
    private final OperationType opType;
    private final TokenType type;

    public OperationToken(char token) {
        this.type = TokenType.OP;
        this.opType = getOpType(token);
    }

    private OperationType getOpType(char token) {
        switch (token) {
            case '+': return OperationType.ADD;
            case '-': return OperationType.SUB;
            case '*': return OperationType.MULT;
            case '/': return OperationType.DIV;
            case '^': return OperationType.POW;
            default: return OperationType.UNDEF;
        }
    }

    public OperationType getOperationType() {
        return opType;
    }

    @Override
    public TokenType getTokenType() {
        return type;
    }
}

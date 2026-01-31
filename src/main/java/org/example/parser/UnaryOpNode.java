package org.example.parser;

import org.example.scanner.OperationToken;
import org.example.scanner.OperationType;
import org.example.scanner.Token;

public class UnaryOpNode extends Node{
    private final Node node;

    public UnaryOpNode(Token token, NodeType type, Node node) {
        super(token, type);
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public OperationType getOperator() {
        OperationToken operationToken = (OperationToken) getToken();
        return operationToken.getOperationType();
    }
}

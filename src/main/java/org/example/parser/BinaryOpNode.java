package org.example.parser;

import org.example.scanner.OperationToken;
import org.example.scanner.OperationType;
import org.example.scanner.Token;

public class BinaryOpNode extends Node{
    private final Node left;
    private final Node right;

    public BinaryOpNode(Token token, NodeType type, Node left, Node right) {
        super(token, type);
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public OperationType getOperator() {
        OperationToken operationToken = (OperationToken) getToken();
        return operationToken.getOperationType();
    }
}

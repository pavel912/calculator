package org.example.parser;

import org.example.scanner.Token;

public abstract class Node {
    private final Token token;
    private final NodeType type;

    public Node(Token token, NodeType type) {
        this.token = token;
        this.type = type;
    }

    public Token getToken() {
        return token;
    }

    public NodeType getType() {
        return type;
    }
}

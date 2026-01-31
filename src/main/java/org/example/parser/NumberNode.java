package org.example.parser;

import org.example.scanner.NumberToken;
import org.example.scanner.Token;

public class NumberNode extends Node {
    public NumberNode(Token token, NodeType type) {
        super(token, type);
    }

    public double getValue() {
        NumberToken num = (NumberToken) getToken();
        return num.getToken();
    }
}

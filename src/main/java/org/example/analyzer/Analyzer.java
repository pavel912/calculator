package org.example.analyzer;

import org.example.parser.*;

import java.util.logging.Logger;

public class Analyzer {
    private final Node parseTree;
    private final Logger logger;

    public Analyzer(Node parseTree, Logger logger) {
        this.parseTree = parseTree;
        this.logger = logger;
    }

    public int computeMaxCognitiveComplexity() {
        return maxCC(parseTree, 0);
    }

    private int maxCC(Node node, int complexity) {
        switch (node.getType()) {
            case UNARY_OP -> {
                return maxCC(((UnaryOpNode) node).getNode(), complexity + 1);
            }

            case BINARY_OP -> {
                BinaryOpNode binOpNode = (BinaryOpNode) node;
                return Math.max(maxCC(binOpNode.getLeft(), complexity + 1), maxCC(binOpNode.getRight(), complexity + 1));
            }

            default -> {
                return complexity;
            }
        }
    }
}

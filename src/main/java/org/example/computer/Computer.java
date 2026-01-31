package org.example.computer;

import org.example.parser.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Computer {
    private final Node parseTree;
    private final Logger logger;

    public Computer(Node parseTree, Logger logger) {
        this.parseTree = parseTree;
        this.logger = logger;
    }

    public double compute() {
        return computeExpr(parseTree);
    }

    private double computeExpr(Node node) {
        switch (node.getType()) {
            case NUMBER -> {
                return ((NumberNode) node).getValue();
            }
            case BINARY_OP -> {
                return computeBinExpr((BinaryOpNode) node);
            }
            case UNARY_OP -> {
                return computeUnExpr((UnaryOpNode) node);
            }
            default -> {
                logger.log(Level.SEVERE, "Incorrect node type");
                System.exit(1);
            }
        }

        return 0;
    }

    private double computeBinExpr(BinaryOpNode opNode) {
        double left = computeExpr(opNode.getLeft());
        double right = computeExpr(opNode.getRight());

        switch (opNode.getOperator()) {
            case ADD -> {
                return left + right;
            }
            case SUB -> {
                return left - right;
            }
            case MULT -> {
                return left * right;
            }
            case DIV -> {
                return left / right;
            }
            case POW -> {
                return Math.pow(left, right);
            }
            default -> {
                logger.log(Level.SEVERE, "Incorrect binary operator");
                System.exit(1);
            }
        }

        return 0;
    }

    private double computeUnExpr(UnaryOpNode opNode) {
        double val = computeExpr(opNode.getNode());

        switch (opNode.getOperator()) {
            case ADD -> {
                return val;
            }
            case SUB -> {
                return -1 * val;
            }
            default -> {
                logger.log(Level.SEVERE, "Incorrect unary operator");
                System.exit(1);
            }
        }

        return 0;
    }
}

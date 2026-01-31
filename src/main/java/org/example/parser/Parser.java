package org.example.parser;

import org.example.scanner.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Parser {
    private final Scanner scanner;
    private final Logger logger;

    public Parser(Scanner scanner, Logger logger) {
        this.scanner = scanner;
        this.logger = logger;
    }

    private Token getNext() {
        if (!scanner.hasNext()) {
            logger.log(Level.SEVERE, "End of input");
            throw new RuntimeException("End of input");
        }

        return scanner.next();
    }

    private Token peek() {
        if (!scanner.hasNext()) {
            logger.log(Level.SEVERE, "End of input");
            throw new RuntimeException("End of input");
        }

        return scanner.peek();
    }

    public Node parse() {
        return expr();
    }

    // expression = ["+"|"-"] term {("+"|"-") term}
    private Node expr() {
        Token opToken = peek();

        if (opToken.getTokenType() == TokenType.OP) {
            OperationToken operationToken = (OperationToken) opToken;
            if (operationToken.getOperationType() == OperationType.ADD || operationToken.getOperationType() == OperationType.SUB) {
                scanner.next();
            } else {
                logger.log(Level.SEVERE, "Incorrect unary operation. Expected: +-, Got: " + operationToken.getOperationType());
                throw new RuntimeException("Incorrect unary operation. Expected: +-, Got: " + operationToken.getOperationType());
            }
        } else {
            opToken = null;
        }

        Node term = term();

        if (opToken != null) {
            term = new UnaryOpNode(opToken, NodeType.UNARY_OP, term);
        }

        while (scanner.hasNext() && scanner.peek().getTokenType() == TokenType.OP) {
            Token opToken2 = scanner.peek();
            OperationToken operationToken = (OperationToken) opToken2;

            if (!(operationToken.getOperationType() == OperationType.ADD || operationToken.getOperationType() == OperationType.SUB)) {
                break;
                //logger.log(Level.SEVERE, "Incorrect binary operation. Expected: +-, Got: " + operationToken.getOperationType());
                //System.exit(1);
            }

            scanner.next();

            Node secondTerm = term();
            term = new BinaryOpNode(opToken2, NodeType.BINARY_OP, term, secondTerm);
        }

        return term;
    }

    // term = factor {("*" | "/") factor}
    private Node term() {
        Node factor = factor();

        while (scanner.hasNext() && scanner.peek().getTokenType() == TokenType.OP) {
            Token opToken = scanner.peek();
            OperationToken operationToken = (OperationToken) opToken;

            if (!(operationToken.getOperationType() == OperationType.MULT || operationToken.getOperationType() == OperationType.DIV)) {
                break;
                //logger.log(Level.SEVERE, "Incorrect operation. Expected: */, Got: " + operationToken.getOperationType());
                //System.exit(1);
            }

            scanner.next();

            Node secondFactor = factor();
            factor = new BinaryOpNode(opToken, NodeType.BINARY_OP, factor, secondFactor);
        }

        return factor;
    }

    // factor = (number | "(" expression ")") ["^" factor]
    private Node factor() {
        Token token = peek();

        Node node = null;

        if (token.getTokenType() == TokenType.NUM) {
            node =  new NumberNode(scanner.next(), NodeType.NUMBER);
        } else if (token.getTokenType() == TokenType.BRACKET) {
            BracketToken brToken = (BracketToken) scanner.next();

            if (brToken.getBracketType() != BracketType.OPEN) {
                logger.log(Level.SEVERE, "Incorrect bracket. Expected: OPEN, Got: " + brToken.getBracketType());
                throw new RuntimeException("Incorrect bracket. Expected: OPEN, Got: " + brToken.getBracketType());
            }

            node = expr();

            token = peek();

            if (token.getTokenType() != TokenType.BRACKET) {
                logger.log(Level.SEVERE, "No closing bracket");
                throw new RuntimeException("No closing bracket");
            }

            BracketToken brToken2 = (BracketToken) scanner.next();

            if (brToken2.getBracketType() != BracketType.CLOSE) {
                logger.log(Level.SEVERE, "Incorrect bracket. Expected: CLOSE, Got: " + brToken.getBracketType());
                throw new RuntimeException("Incorrect bracket. Expected: CLOSE, Got: " + brToken.getBracketType());
            }
        } else {
            logger.log(Level.SEVERE, "Invalid token. Expected: NUM or (expr), Got: " + token.getTokenType());
            throw new RuntimeException("Invalid token. Expected: NUM or (expr), Got: " + token.getTokenType());
        }

        if (scanner.hasNext() && scanner.peek().getTokenType() == TokenType.OP) {
            OperationToken pow = (OperationToken) scanner.peek();

            if (!(pow.getOperationType() == OperationType.POW)) {
                return node;
            }

            scanner.next();

            Node powerNode = factor();

            node = new BinaryOpNode(pow, NodeType.BINARY_OP, node, powerNode);
        }

        return node;
    }
}

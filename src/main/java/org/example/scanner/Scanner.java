package org.example.scanner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Scanner implements Iterator<Token> {
    private int index;
    private final List<Token> tokens;

    public Scanner(String content) {
        this.index = 0;
        this.tokens = tokenize(content.split(" "));
    }

    private List<Token> tokenize(String[] content) {
        List<Token> tokens = new ArrayList<>();

        for (String s: content) {
            List<Character> digits = new ArrayList<>();

            for (char c: s.toCharArray()) {
                CharType ct = getCharType(c);

                if (ct == CharType.NUM) {
                    digits.add(c);
                } else {
                    if (!digits.isEmpty()) {
                        tokens.add(new NumberToken(buildnumber(digits)));
                        digits.clear();
                    }

                    if (ct == CharType.OP) tokens.add(new OperationToken(c));
                    else if (ct == CharType.BRACKET) tokens.add(new BracketToken(c));
                    else tokens.add(new UndefToken(c));
                }
            }

            if (!digits.isEmpty()) {
                tokens.add(new NumberToken(buildnumber(digits)));
                digits.clear();
            }
        }

        return tokens;
    }

    private CharType getCharType(char c) {
        if (Character.isDigit(c) || c == '.') {
            return CharType.NUM;
        }

        switch (c) {
            case '+':;
            case '-':;
            case '*':;
            case '/':;
            case '^': return CharType.OP;
            case '(':;
            case ')': return CharType.BRACKET;
            default: return CharType.UNDEF;
        }
    }

    private double buildnumber(List<Character> digits) {
        StringBuilder sb = new StringBuilder();

        for (Character d: digits) {
            sb.append(d);
        }

        return Double.parseDouble(sb.toString());
    }

    @Override
    public boolean hasNext() {
        return index < tokens.size();
    }

    @Override
    public Token next() {
        return tokens.get(index++);
    }

    public Token peek() {
        return tokens.get(index);
    }
}

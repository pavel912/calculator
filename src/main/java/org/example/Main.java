package org.example;

import org.example.analyzer.Analyzer;
import org.example.computer.Computer;
import org.example.parser.Node;
import org.example.parser.Parser;

import java.util.Scanner;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        System.out.println("This is a simple calculator for integers and float. It supports addition (+), subtraction (-), multiplication (*), division (/) and pow (^) with parenthesis. Just type you expression and it will compute a value.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your expression or press Enter to exit: ");

        String expression = scanner.nextLine();
        Parser parser = new Parser(new org.example.scanner.Scanner(expression), Logger.getLogger("parserLogger"));
        Node parseTree = parser.parse();

        Computer computer = new Computer(parseTree, Logger.getLogger("ComputerLogger"));
        Analyzer analyzer = new Analyzer(parseTree, Logger.getLogger("AnalyzerLogger"));
        System.out.println("Result: " + computer.compute());
        System.out.println("Complexity of the expression: " + analyzer.computeMaxCognitiveComplexity());
    }
}
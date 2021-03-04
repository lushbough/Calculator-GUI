package edu.csc413.calculator.evaluator;
import edu.csc413.calculator.exceptions.InvalidTokenException;
import edu.csc413.calculator.operators.*;
import java.util.Stack;
import java.util.StringTokenizer;

public class Evaluator {

    private Stack<Operand> operandStack;
    private Stack<Operator> operatorStack;
    private StringTokenizer expressionTokenizer;
    private final String delimiters = " +/*-^()";

    public Evaluator() {
        operandStack = new Stack<>();
        operatorStack = new Stack<>();
    }

    public int evaluateExpression(String expression) throws InvalidTokenException {
        String expressionToken;
        if (operatorStack.isEmpty()) {
            operatorStack.push(new BlankOperator());
        }

        this.expressionTokenizer = new StringTokenizer(expression, this.delimiters, true);

        while (this.expressionTokenizer.hasMoreTokens()) {
            // filter out spaces
            if (!(expressionToken = this.expressionTokenizer.nextToken()).equals(" ")) {
                // check if token is an operand
                if (Operand.check(expressionToken)) {
                    operandStack.push(new Operand(expressionToken));
                } else {
                    if (!Operator.check(expressionToken)) {
                        throw new InvalidTokenException(expressionToken);
                    }

                    Operator newOperator = Operator.getOperator(expressionToken);

                    if (expressionToken.equals("(")) {
                        operatorStack.push(newOperator);
                    }
                    if (expressionToken.equals(")")) {
                        while (operatorStack.peek().priority() > 0) {
                            process();
                        }
                        operatorStack.pop();
                    }

                    if (checkParenthesis(expressionToken)) {
                        if (!operatorStack.isEmpty() && Operator.check(expressionToken)) {
                            while (operatorStack.peek().priority() >= newOperator.priority()) {
                                process();
                            }
                            operatorStack.push(newOperator);
                        }
                    }
                }
            }
        }

        while (!operatorStack.isEmpty() && operandStack.size() != 1) {
            process();
        }
        Operand solution = operandStack.pop();
        return solution.getValue();
    }

    private void process() {
        Operator operatorFromStack = operatorStack.pop();
        Operand operandTwo = operandStack.pop();
        Operand operandOne = operandStack.pop();
        Operand result = operatorFromStack.execute(operandOne, operandTwo);
        operandStack.push(result);
    }

    private boolean checkParenthesis(String expressionToken) {
        return (!expressionToken.equals(")")) && (!expressionToken.equals("("));
    }
}

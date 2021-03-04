package edu.csc413.calculator.operators;
import edu.csc413.calculator.evaluator.Operand;
import java.util.HashMap;

public abstract class Operator {

    private static HashMap<String, Operator> operators;

    static {
        operators = new HashMap<>();
        operators.put("+", new AddOperator());
        operators.put("-", new SubtractOperator());
        operators.put("/", new DivideOperator());
        operators.put("*", new MultiplyOperator());
        operators.put("^", new PowerOperator());
        operators.put("(", new LeftParenthesisOperator());
        operators.put(")", new RightParenthesisOperator());
        operators.put("blank", new BlankOperator());
    }

    public abstract int priority();

    public abstract Operand execute(Operand operandOne, Operand operandTwo);

    public static Operator getOperator(String token) {
        return operators.get(token);
    }

    public static boolean check(String token) {
        return operators.containsKey(token);
    }
}

package edu.csc413.calculator.operators;

import edu.csc413.calculator.evaluator.Operand;

public class PowerOperator extends Operator {
    @Override
    public int priority() {
        return 3;
    }

    @Override
    public Operand execute(Operand op1, Operand op2) {
        Operand op3 = new Operand((int) Math.pow(op1.getValue(), op2.getValue()));
        return op3;
    }
}

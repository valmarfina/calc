package tools;

import java.util.LinkedList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    final static Pattern numPatt = Pattern.compile("(-?\\d+)(.*)");
    static LinkedList<Calculator> operList = new LinkedList<>();
    static LinkedList<Double> numList = new LinkedList<>();

    int prior;
    Operation operation;

    Calculator(int p, Operation o) {
        prior = p;
        operation = o;
    }

    double calc(double o1, double o2) {
        return operation.calc(o1, o2);
    }

    static void calculate(Calculator c) {
        while (operList.peek() != c)
            numList.addFirst(Objects.requireNonNull(operList.poll()).calc(numList.poll(), numList.poll()));
    }

    public static void getResult(String expr) {
        expr = expr.replaceAll(" ", "");
        try {
            for (; ; ) {
                Matcher m = numPatt.matcher(expr);
                if (!m.matches())
                    throw new Exception("Не число");
                numList.addFirst(Double.parseDouble(m.group(1)));
                expr = m.group(2);

                if (expr.length() == 0)
                    break;

                Calculator c = switch (expr.charAt(0)) {
                    case '*' -> new Calculator(1, (op1, op2) -> op2 * op1);
                    case '/' -> new Calculator(1, (op1, op2) -> op2 / op1);
                    case '+' -> new Calculator(2, (op1, op2) -> op2 + op1);
                    case '-' -> new Calculator(2, (op1, op2) -> op2 - op1);
                    default -> throw new Exception("Not operation");
                };
                operList.add(c);
                operList.sort((o1, o2) -> o1.prior < o2.prior ? -1 : 1);
                calculate(c);
                expr = expr.substring(1);
            }
            calculate(null);
        } catch (Exception e) {
            System.out.println("неверный ввод");
        }

        System.out.println("результат: " + numList.poll());
    }
}


package tools;

import static tools.Calculator.getResult;

public class Addtion {
    private String expr;

    public String getExpr() {
        return expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public static void getDoneResult(String expr) {
        getResult(expr);
    }
}

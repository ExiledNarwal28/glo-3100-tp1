package ca.ulaval.glo3100.polynomials;

import java.util.List;

public class Polynomial<T> {

    private List<T> coefficients;

    public Polynomial(List<T> coefficients) {
        this.coefficients = coefficients;
    }

    // TODO Polynomial.getResult
    public T getResult(T x) {
        return x;
    }

    // TODO Polynomial.toString
    @Override
    public String toString() {
        return "";
    }
}

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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("f(x) = ");

        if (!coefficients.isEmpty()) {
            stringBuilder.append(String.format("%s * ", coefficients.get(0)));
        }

        for (int i = 1; i < coefficients.size(); i++) {
            stringBuilder.append(String.format("%s x", coefficients.get(i)));

            if (i > 1) {
                stringBuilder.append(String.format("^(%s)", i));
            }

            // If not last
            if (i != coefficients.size() - 1) {
                stringBuilder.append(" * ");
            }
        }

        return stringBuilder.toString();
    }
}

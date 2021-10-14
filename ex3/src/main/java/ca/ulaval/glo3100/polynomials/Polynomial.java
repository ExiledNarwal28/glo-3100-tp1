package ca.ulaval.glo3100.polynomials;

import java.util.List;

public class Polynomial {

    private List<Integer> coefficients;

    public Polynomial(List<Integer> coefficients) {
        this.coefficients = coefficients;
    }

    /**
     * @param x variable to get result from
     * @return f(x) for given x
     */
    public int getResult(int x) {
        int result = 0;

        for (int i = 0; i < coefficients.size(); i++) {
            result += (coefficients.get(i) * Math.pow(x, i));
        }

        return result;
    }

    /**
     * @return Polynomial in form of "f(x) = a + b x + c x^(2)"
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("f(x) = ");

        if (!coefficients.isEmpty()) {
            stringBuilder.append(String.format("%s + ", coefficients.get(0)));
        }

        for (int i = 1; i < coefficients.size(); i++) {
            stringBuilder.append(String.format("%s x", coefficients.get(i)));

            if (i > 1) {
                stringBuilder.append(String.format("^(%s)", i));
            }

            // If not last
            if (i != coefficients.size() - 1) {
                stringBuilder.append(" + ");
            }
        }

        return stringBuilder.toString();
    }
}

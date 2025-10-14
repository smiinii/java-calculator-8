package calculator.domain;

public class Calculator {

    public int calculate(String input) {
        String resultValue = convertEmptyToZero(input);
        return Integer.parseInt(resultValue);
    }

    private String convertEmptyToZero(String input) {
        if (input.isEmpty()) {
            return "0";
        }
        return input;
    }

}

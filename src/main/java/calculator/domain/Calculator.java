package calculator.domain;

import java.util.List;

public class Calculator {

    private final Delimiter delimiter;

    public Calculator(Delimiter delimiter) {
        this.delimiter = delimiter;
    }

    public int calculate(String inputs) {
        if (inputs.isEmpty()) {
            return 0;
        }
        List<String> tokens = delimiter.tokenize(inputs);
        return sumTokens(tokens);
    }

    private int sumTokens(List<String> tokens) {
        int sum = 0;

        for (String token : tokens) {
            validateNumber(token);
            int numbers = Integer.parseInt(token);
            validateNotNegative(numbers);
            sum += numbers;
        }
        return sum;
    }

    private void validateNumber(String token) {
        if (!token.matches("\\d+")) {
            throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다");
        }
    }

    private void validateNotNegative(int numbers) {
        if (numbers < 0) {
            throw new IllegalArgumentException("음수는 허용되지 않습니다");
        }
    }
}
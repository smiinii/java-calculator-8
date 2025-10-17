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
        List<String> tokens = delimiter.detectAndSplit(inputs);
        return tokens.stream()
                .map(Operand::new)
                .mapToInt(Operand::value)
                .sum();
    }
}
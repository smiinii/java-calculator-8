package calculator.domain;

import java.util.ArrayList;
import java.util.Arrays;
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
        return tokens.stream()
                .map(Number::new)
                .mapToInt(Number::getNumber)
                .sum();
    }
}
package calculator;

import calculator.domain.Calculator;
import calculator.domain.Delimiter;
import calculator.view.IoView;

public class Application {
    public static void main(String[] args) {
        IoView io = new IoView();
        Delimiter delimiter = new Delimiter();
        Calculator calculator = new Calculator(delimiter);

        String input = io.input();
        int result = calculator.calculate(input);
        io.printResult(result);
    }
}

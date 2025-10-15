package calculator;

import calculator.domain.Calculator;
import calculator.domain.Delimiter;
import calculator.view.IO;

public class Application {
    public static void main(String[] args) {
        IO io = new IO();
        Delimiter delimiter = new Delimiter();
        Calculator calculator = new Calculator(delimiter);

        String input = io.input();
        int result = calculator.calculate(input);
        io.printResult(result);
    }
}

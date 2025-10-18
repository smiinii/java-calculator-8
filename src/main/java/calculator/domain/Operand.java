package calculator.domain;

public class Operand {

    private static final String DIGITS = "\\d+";
    private final int number;

    public Operand(String token) {
        if (token.isEmpty()) {
            this.number = 0;
            return;
        }
        validateNumber(token);
        int num = Integer.parseInt(token);
        validateNotNegative(num);
        this.number = num;
    }

    private void validateNumber(String token) {
        if (!token.matches(DIGITS)) {
            throw new IllegalArgumentException("숫자가 아닌 값이 포함되어 있습니다");
        }
    }

    private void validateNotNegative(int numbers) {
        if (numbers < 0) {
            throw new IllegalArgumentException("음수는 허용되지 않습니다");
        }
    }

    public int value() {
        return number;
    }

}

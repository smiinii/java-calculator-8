package calculator.domain;

public class Number {

    private final int number;

    public Number(String number) {
        validateNumber(number);
        int num = Integer.parseInt(number);
        validateNotNegative(num);
        this.number = num;
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

    public int getNumber() {
        return number;
    }

}

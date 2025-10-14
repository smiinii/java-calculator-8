package calculator;

import calculator.domain.Calculator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CalculatorTest {

    @Test
    void 빈문자열을_입력하면_0을_반환한다() {
        Calculator c = new Calculator();
        assertThat(c.calculate("")).isEqualTo(0);
    }
}

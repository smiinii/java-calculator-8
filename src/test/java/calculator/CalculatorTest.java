package calculator;


import calculator.domain.Calculator;
import calculator.domain.Delimiter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class CalculatorTest {

    @Test
    void 빈문자열을_입력하면_0을_반환한다() {
        Delimiter delimiter = new Delimiter();
        Calculator calculator = new Calculator(delimiter);
        assertThat(calculator.calculate("")).isEqualTo(0);
    }

    @Test
    void 기본_구분자로_합산된다() {
        Delimiter delimiter = new Delimiter();
        Calculator calculator = new Calculator(delimiter);
        assertThat(calculator.calculate("1,2,3")).isEqualTo(6);
        assertThat(calculator.calculate("4:5:6")).isEqualTo(15);
        assertThat(calculator.calculate("1,2:3")).isEqualTo(6);
    }

    @Test
    void 커스텀_구분자로_합산된다() {
        Delimiter delimiter = new Delimiter();
        Calculator calculator = new Calculator(delimiter);
        assertThat(calculator.calculate("//;\\n1;2;3")).isEqualTo(6);
        assertThat(calculator.calculate("//|\\n4|5|6")).isEqualTo(15);
    }

    @Test
    void 숫자의_자릿수는_상관_없다() {
        Delimiter delimiter = new Delimiter();
        Calculator calculator = new Calculator(delimiter);
        assertThat(calculator.calculate("11,2,3")).isEqualTo(16);
        assertThat(calculator.calculate("4:15:6")).isEqualTo(25);
        assertThat(calculator.calculate("1,2:113")).isEqualTo(116);
    }

}

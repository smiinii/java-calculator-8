package calculator;

import calculator.domain.Calculator;
import calculator.domain.Delimiter;
import calculator.domain.Operand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class OperandTest {

    @Test
    void 빈문자열을_입력하면_0을_반환한다() {
        assertThat(new Operand("").value()).isEqualTo(0);
    }

    @Test
    void 숫자가_아닌_값이_포함되면_예외() {
        assertThatThrownBy(() -> new Operand("a"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 음수가_포함되면_예외() {
        assertThatThrownBy(() -> new Operand("-1"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

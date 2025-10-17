package calculator;

import calculator.domain.Operand;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class OperandTest {

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

package calculator;

import calculator.domain.Delimiter;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DefaultDelimiterTest {

    @Test
    void 기본_구분자로_분리() {
        Delimiter defaultDelimiter = new Delimiter();
        List<String> tokens = defaultDelimiter.detectAndSplit("1,2:3");
        assertThat(tokens).containsExactly("1", "2", "3");
    }

    @Test
    void 구분자_없으면_그대로_반환() {
        Delimiter defaultDelimiter = new Delimiter();
        List<String> tokens = defaultDelimiter.detectAndSplit("123");
        assertThat(tokens).containsExactly("123");
    }

    @Test
    void 기본_구분자가_아니면_예외() {
        Delimiter defaultDelimiter = new Delimiter();
        assertThatThrownBy(() -> defaultDelimiter.detectAndSplit("1;2;3"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 기본_구분자_사이에_공백이_있으면_예외() {
        Delimiter d = new Delimiter();
        assertThatThrownBy(() -> d.detectAndSplit("1, 2:3"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> d.detectAndSplit(" 1,2:3"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> d.detectAndSplit("1,2:3 "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 연속_선행_후행_구분자_예외() {
        Delimiter d = new Delimiter();
        AssertionsForClassTypes.assertThatThrownBy(() -> d.detectAndSplit("1;;2"))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsForClassTypes.assertThatThrownBy(() -> d.detectAndSplit("1,,2"))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsForClassTypes.assertThatThrownBy(() -> d.detectAndSplit(";1;2"))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsForClassTypes.assertThatThrownBy(() -> d.detectAndSplit("1;2;"))
                .isInstanceOf(IllegalArgumentException.class);
        AssertionsForClassTypes.assertThatThrownBy(() -> d.detectAndSplit("1,|2"))
                .isInstanceOf(IllegalArgumentException.class);
    }

}

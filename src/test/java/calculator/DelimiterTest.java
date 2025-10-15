package calculator;

import calculator.domain.Delimiter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DelimiterTest {

    @Test
    void 기본_구분자로_분리() {
        Delimiter defaultDelimiter = new Delimiter();
        List<String> tokens = defaultDelimiter.tokenize("1,2:3");
        assertThat(tokens).containsExactly("1", "2", "3");
    }

    @Test
    void 구분자_없으면_그대로_반환() {
        Delimiter defaultDelimiter = new Delimiter();
        List<String> tokens = defaultDelimiter.tokenize("123");
        assertThat(tokens).containsExactly("123");
    }

    @Test
    void 기본_구분자가_아니면_예외() {
        Delimiter defaultDelimiter = new Delimiter();
        assertThatThrownBy(() -> defaultDelimiter.tokenize("1;2;3"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 아무_문자열_입력시_예외() {
        Delimiter defaultDelimiter = new Delimiter();
        List<String> tokens = defaultDelimiter.tokenize("1, 2 :3 ");
        assertThat(tokens).containsExactly("123");
    }

    @Test
    void 기본_구분자_사이에_공백이_있으면_예외() {
        Delimiter d = new Delimiter();
        assertThatThrownBy(() -> d.tokenize("1, 2:3"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> d.tokenize(" 1,2:3"))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> d.tokenize("1,2:3 "))
                .isInstanceOf(IllegalArgumentException.class);
    }

}

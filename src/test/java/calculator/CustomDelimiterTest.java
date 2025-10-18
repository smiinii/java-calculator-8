package calculator;

import calculator.domain.Delimiter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class CustomDelimiterTest {
    @Test
    void 커스텀_구분자_분리() {
        Delimiter d = new Delimiter();
        assertThat(d.detectAndSplit("//;\\n1,2;3")).containsExactly("1","2","3");
    }

    @Test
    void 커스텀_구분자_형식이_다르면_예외() {
        Delimiter d = new Delimiter();
        assertThatThrownBy(() -> d.detectAndSplit("/;\\n1,2;3"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.detectAndSplit("//;\n1,2;3"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.detectAndSplit("//;\t1,2;3"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.detectAndSplit(";\t1,2;3"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.detectAndSplit("//;1,2;3"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 커스텀_구분자_한글자_넘어가면_예외() {
        Delimiter d = new Delimiter();
        assertThatThrownBy(() -> d.detectAndSplit("//;[\\n1,2;3"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.detectAndSplit("//;;\\n1,2;3"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.detectAndSplit("//.;\\n1,2;3"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 커스텀_구분자_공백_또는_숫자시_예외() {
        Delimiter d = new Delimiter();
        assertThatThrownBy(() -> d.detectAndSplit("//\\n1,2;3"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.detectAndSplit("//5\\n1,2;3"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 커스텀_구분자_또는_기본_구분자가_아닐시_예외() {
        Delimiter d = new Delimiter();
        assertThatThrownBy(() -> d.detectAndSplit("//;\\n1,2;3.4"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 본문_부분이_비어있으면_공백_처리() {
        Delimiter d = new Delimiter();
        assertThat(d.detectAndSplit("//;\\n")).containsExactly("");
    }

    @Test
    void 연속_선행_후행_구분자_예외() {
        Delimiter d = new Delimiter();
        assertThatThrownBy(() -> d.detectAndSplit("//;\\n1;;2"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.detectAndSplit("//;\\n1,,2"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.detectAndSplit("//;\\n;1;2"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.detectAndSplit("//;\\n1;2;"))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> d.detectAndSplit("//|\\n1,|2"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}

package calculator.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Delimiter {

    private static final String DEFAULT_DELIMITER = "[,:]";
    private static final String VALIDATE_DEFAULT = ".*[^0-9,:].*";

    public List<String> tokenize(String inputs) {
        if (isDefault(inputs)) {
            return defaultTokenize(inputs);
        }
        throw new UnsupportedOperationException("커스텀 구분자 아직 미구현");
    }

    private List<String> defaultTokenize(String inputs) {
        validateDefaultOnly(inputs);
        return Arrays.stream(inputs.split(DEFAULT_DELIMITER)).toList();
    }

    private boolean isDefault(String inputs) {
        return !inputs.startsWith("//");
    }

    private void validateDefaultOnly(String inputs) {
        if (inputs.matches(VALIDATE_DEFAULT)) {
            throw new IllegalArgumentException("기본 구분자는 쉼표(,) 또는 콜론(:)만 허용됩니다.");
        }
    }



}

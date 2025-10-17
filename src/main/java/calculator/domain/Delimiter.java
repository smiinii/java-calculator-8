package calculator.domain;

import java.util.Arrays;
import java.util.List;

public class Delimiter {

    private static final String BASE_DELIMS = ",:";
    private static final String DEFAULT_DELIMITER = "[" + BASE_DELIMS + "]";
    private static final String VALIDATE_DEFAULT = ".*[^0-9" + BASE_DELIMS + "].*";
    private static final String VALIDATE_DELIMITER_SEQUENCE = "^\\d+(?:[" + BASE_DELIMS + "]\\d+)*$";
    private static final String CUSTOM_DELIMITER_START = "//";
    private static final String CUSTOM_DELIMITER_END = "\\n";

    public List<String> detectAndSplit(String inputs) {
        if (isDefault(inputs)) {
            return splitByDefaultDelimiter(inputs);
        }
        return splitByCustomDelimiter(inputs);
    }

    private boolean isDefault(String inputs) {
        return !inputs.startsWith("//");
    }

    private List<String> splitByDefaultDelimiter(String inputs) {
        if (inputs.matches(VALIDATE_DEFAULT)) {
            throw new IllegalArgumentException("기본 구분자(" + BASE_DELIMS + ")만 허용됩니다.");
        }
        validateDelimiterSequence(inputs);
        return Arrays.stream(inputs.split(DEFAULT_DELIMITER)).toList();
    }

    private List<String> splitByCustomDelimiter(String inputs) {
        int lnIndex = validateCustomFormat(inputs);
        char customDelimiter = extractCustomDelimiter(inputs, lnIndex);
        String numbersPart = extractNumberPart(inputs, lnIndex);
        String normalized =  normalizeDelimiters(numbersPart, customDelimiter);

        validateDelimiterSequence(normalized);
        if (normalized.matches(VALIDATE_DEFAULT)) {
            throw new IllegalArgumentException("기본 구분자(쉼표(" + BASE_DELIMS + ")와 커스텀 구분자(" + customDelimiter +")만 허용됩니다.");
        }
        return Arrays.stream(normalized.split(DEFAULT_DELIMITER)).toList();
    }

    private void validateDelimiterSequence(String inputs) {
        if (!inputs.matches(VALIDATE_DELIMITER_SEQUENCE)) {
            throw new IllegalArgumentException("구분자 사용이 올바르지 않습니다.");
        }
    }

    private int validateCustomFormat(String inputs) {
        if (!inputs.startsWith(CUSTOM_DELIMITER_START)) {
            throw new IllegalArgumentException("커스텀 형식은 '" + CUSTOM_DELIMITER_START + "'으로 시작해야 합니다.");
        }
        int lnIndex = inputs.indexOf(CUSTOM_DELIMITER_END);
        if (lnIndex < 0) {
            throw new IllegalArgumentException("커스텀 형식은 '\n'이 존재해야 합니다.");
        }
        return lnIndex;
    }

    private char extractCustomDelimiter(String inputs, int lnIndex) {
        int start = CUSTOM_DELIMITER_START.length();
        String customDelimiter = inputs.substring(start, lnIndex);
        if (customDelimiter.length() != 1) {
            throw new IllegalArgumentException("커스텀 구분자는 한 글자만 허용됩니다.");
        }

        char customToken = customDelimiter.charAt(0);
        if (Character.isWhitespace(customToken) || Character.isDigit(customToken)) {
            throw new IllegalArgumentException("커스텀 구분자로 공백과 숫자는 허용되지 않습니다.");
        }
        return customToken;
    }

    private String extractNumberPart(String inputs, int lnIndex) {
        int startNumberPart = lnIndex + CUSTOM_DELIMITER_END.length();
        if (startNumberPart == inputs.length()) {
            throw new IllegalArgumentException("숫자 부분이 비어있습니다.");
        }
        return inputs.substring(startNumberPart);
    }

    private String normalizeDelimiters(String numbersPart, char customToken) {
        char defaultDelimiter = BASE_DELIMS.charAt(0);
        return numbersPart.replace(customToken, defaultDelimiter);
    }
}

package calculator.domain;

import java.util.Arrays;
import java.util.List;

public class Delimiter {

    private static final String DEFAULT_DELIMS = ",:";
    private static final String CUSTOM_HEADER_START = "//";
    private static final String CUSTOM_HEADER_END = "\\n";

    private static final String HAS_NON_DEFAULT_PUNCT =
            ".*[\\p{Punct}&&[^" + DEFAULT_DELIMS + "\\-]].*";

    private static final String DELIMITER_CLASS = "[" + DEFAULT_DELIMS + "]";
    private static final String TOKEN = "[^\\s" + DEFAULT_DELIMS + "]+";
    private static final String WELL_FORMED_SEQUENCE =
            "^" + TOKEN + "(?:" + DELIMITER_CLASS + TOKEN + ")*$";


    public List<String> detectAndSplit(String inputs) {
        if (isDefault(inputs)) {
            return splitByDefaultDelimiter(inputs);
        }
        return splitByCustomDelimiter(inputs);
    }

    private boolean isDefault(String inputs) {
        return !inputs.startsWith(CUSTOM_HEADER_START);
    }

    private List<String> splitByDefaultDelimiter(String inputs) {
        if (inputs.isEmpty()) {
            return List.of("");
        }
        validateOnlyDefaultDelims(inputs);
        validateWellFormedSequence(inputs);
        return Arrays.stream(inputs.split(DELIMITER_CLASS)).toList();
    }

    private List<String> splitByCustomDelimiter(String inputs) {
        validateCustomHeaderStart(inputs);
        int lnIndex = findHeaderEndOrThrow(inputs);

        String body = extractBody(inputs, lnIndex);
        if (body.isEmpty()) {
            return List.of("");
        }
        char customDelimiter = extractCustomDelimiter(inputs, lnIndex);
        String normalized =  normalizeDelimiters(body, customDelimiter);

        validateOnlyDefaultDelims(normalized);
        validateWellFormedSequence(normalized);
        return Arrays.stream(normalized.split(DELIMITER_CLASS)).toList();
    }

    private void validateOnlyDefaultDelims(String inputs) {
        if(inputs.matches(HAS_NON_DEFAULT_PUNCT)) {
            throw new IllegalArgumentException("정해진 구분자 외에 구분자는 사용할 수 없습니다.");
        }
    }

    private void validateWellFormedSequence(String inputs) {
        if (!inputs.matches(WELL_FORMED_SEQUENCE)) {
            throw new IllegalArgumentException("구분자 사용이 올바르지 않습니다. (선행/후행/연속 금지)");
        }
    }

    private void validateCustomHeaderStart(String input) {
        if (!input.startsWith(CUSTOM_HEADER_START)) {
            throw new IllegalArgumentException("커스텀 형식은 '" + CUSTOM_HEADER_START + "'으로 시작해야 합니다.");
        }
    }

    private int findHeaderEndOrThrow(String input) {
        int lnIndex = input.indexOf(CUSTOM_HEADER_END);
        if (lnIndex < 0) {
            throw new IllegalArgumentException("커스텀 형식은 '\\n'이 존재해야 합니다.");
        }
        return lnIndex;
    }

    private char extractCustomDelimiter(String inputs, int lnIndex) {
        int start = CUSTOM_HEADER_START.length();
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

    private String extractBody(String inputs, int lnIndex) {
        int startNumberPart = lnIndex + CUSTOM_HEADER_END.length();
        if (startNumberPart == inputs.length()) {
            return "";
        }
        return inputs.substring(startNumberPart);
    }

    private String normalizeDelimiters(String body, char customToken) {
        char defaultDelimiter = DEFAULT_DELIMS.charAt(0);
        return body.replace(customToken, defaultDelimiter);
    }
}

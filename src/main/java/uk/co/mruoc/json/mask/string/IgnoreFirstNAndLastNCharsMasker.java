package uk.co.mruoc.json.mask.string;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class IgnoreFirstNAndLastNCharsMasker implements UnaryOperator<String> {

    private static final char DEFAULT_MASK_CHAR = '*';

    private final int firstN;
    private final int lastN;
    private final char maskChar;

    public IgnoreFirstNAndLastNCharsMasker(int firstN, int lastN) {
        this(firstN, lastN, DEFAULT_MASK_CHAR);
    }

    @Override
    public String apply(String value) {
        if (value == null) {
            return mask("null");
        }
        return mask(value);
    }

    private String mask(String value) {
        int maskedLength = value.length() - firstN - lastN;
        if (maskedLength < 0) {
            return value;
        }
        String firstUnmasked = value.substring(0, firstN);
        String masked = StringUtils.repeat(maskChar, maskedLength);
        String lastUnmasked = value.substring(firstN + maskedLength);
        return firstUnmasked + masked + lastUnmasked;
    }

}

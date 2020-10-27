package uk.co.mruoc.json.mask.string;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class IgnoreLastNCharsMasker implements UnaryOperator<String> {

    private static final char DEFAULT_MASK_CHAR = '*';

    private final int lastN;
    private final char maskChar;

    public IgnoreLastNCharsMasker(int lastN) {
        this(lastN, DEFAULT_MASK_CHAR);
    }

    @Override
    public String apply(String value) {
        if (value == null) {
            return mask("null");
        }
        if (value.length() <= lastN) {
            return value;
        }
        return mask(value);
    }

    private String mask(String value) {
        int maskedLength = value.length() - lastN;
        String masked = StringUtils.repeat(maskChar, maskedLength);
        String unmasked = value.substring(maskedLength);
        return masked + unmasked;
    }

}

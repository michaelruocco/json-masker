package uk.co.mruoc.json.mask.string;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class StringMasker implements UnaryOperator<String> {

    private static final char DEFAULT_MASK_CHAR = '*';

    private final char maskChar;

    public StringMasker() {
        this(DEFAULT_MASK_CHAR);
    }

    @Override
    public String apply(String value) {
        if (value == null) {
            return mask(4);
        }
        return mask(value.length());
    }

    private String mask(int length) {
        return StringUtils.repeat(maskChar, length);
    }

}

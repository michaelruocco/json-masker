package uk.co.mruoc.json.mask;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class DefaultValueMasker implements ValueMasker {

    private static final char DEFAULT_MASK_CHAR = '*';

    private final char maskChar;

    public DefaultValueMasker() {
        this(DEFAULT_MASK_CHAR);
    }

    @Override
    public String mask(String value) {
        if (value == null) {
            return mask(4);
        }
        return mask(value.length());
    }

    private String mask(int length) {
        return StringUtils.repeat(maskChar, length);
    }

}

package uk.co.mruoc.json.mask.string;

import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class StringMaskerTest {

    private final String RAW_VALUE = "raw-value";

    @Test
    void shouldMaskNullAs4Chars() {
        UnaryOperator<String> masker = new StringMasker();

        String masked = masker.apply(null);

        assertThat(masked).isEqualTo("****");
    }

    @Test
    void shouldMaskAllCharsWithAsterisk() {
        UnaryOperator<String> masker = new StringMasker();

        String masked = masker.apply(RAW_VALUE);

        assertThat(masked).isEqualTo("*********");
    }

    @Test
    void shouldMaskAllCharsWithCustomMaskChar() {
        UnaryOperator<String> masker = new StringMasker('-');

        String masked = masker.apply(RAW_VALUE);

        assertThat(masked).isEqualTo("---------");
    }

}

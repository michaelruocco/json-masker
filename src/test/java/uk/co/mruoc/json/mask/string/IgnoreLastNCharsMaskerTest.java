package uk.co.mruoc.json.mask.string;

import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class IgnoreLastNCharsMaskerTest {

    private final String RAW_VALUE = "raw-value";

    @Test
    void shouldReturnNullAsString() {
        UnaryOperator<String> masker = new IgnoreLastNCharsMasker(2);

        String masked = masker.apply(null);

        assertThat(masked).isEqualTo("**ll");
    }

    @Test
    void shouldReturnUnmaskedStringIfValueLengthIsLessThanLastN() {
        UnaryOperator<String> masker = new IgnoreLastNCharsMasker(RAW_VALUE.length() + 1);

        String masked = masker.apply(RAW_VALUE);

        assertThat(masked).isEqualTo(RAW_VALUE);
    }

    @Test
    void shouldMaskAllCharsExceptLast3() {
        UnaryOperator<String> masker = new IgnoreLastNCharsMasker(3);

        String masked = masker.apply(RAW_VALUE);

        assertThat(masked).isEqualTo("******lue");
    }

    @Test
    void shouldMaskAllCharsWithCustomMaskCharExceptLast2() {
        UnaryOperator<String> masker = new IgnoreLastNCharsMasker(2, '-');

        String masked = masker.apply(RAW_VALUE);

        assertThat(masked).isEqualTo("-------ue");
    }

}

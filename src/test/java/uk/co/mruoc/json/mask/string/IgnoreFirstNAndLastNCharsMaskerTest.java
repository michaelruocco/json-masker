package uk.co.mruoc.json.mask.string;

import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class IgnoreFirstNAndLastNCharsMaskerTest {

    private final String RAW_VALUE = "raw-value";

    @Test
    void shouldReturnNullAsString() {
        UnaryOperator<String> masker = new IgnoreFirstNAndLastNCharsMasker(1, 1);

        String masked = masker.apply(null);

        assertThat(masked).isEqualTo("n**l");
    }

    @Test
    void shouldReturnUnmaskedStringIfFirstNIsZeroAndValueLengthIsLessThanLastN() {
        UnaryOperator<String> masker = new IgnoreFirstNAndLastNCharsMasker(0, RAW_VALUE.length() + 1);

        String masked = masker.apply(RAW_VALUE);

        assertThat(masked).isEqualTo(RAW_VALUE);
    }

    @Test
    void shouldMaskAllCharsAfterFirstNAndBeforeLastN() {
        UnaryOperator<String> masker = new IgnoreFirstNAndLastNCharsMasker(3, 3);

        String masked = masker.apply(RAW_VALUE);

        assertThat(masked).isEqualTo("raw***lue");
    }

    @Test
    void shouldMaskAllCharsAfterFirstNAndBeforeLastNWithCustomMaskChar() {
        UnaryOperator<String> masker = new IgnoreFirstNAndLastNCharsMasker(2, 2, '-');

        String masked = masker.apply(RAW_VALUE);

        assertThat(masked).isEqualTo("ra-----ue");
    }

}

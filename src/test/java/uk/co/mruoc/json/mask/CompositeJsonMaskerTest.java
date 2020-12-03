package uk.co.mruoc.json.mask;

import org.junit.jupiter.api.Test;

import java.util.function.UnaryOperator;

import static org.assertj.core.api.Assertions.assertThat;

class CompositeJsonMaskerTest {

    private final UnaryOperator<String> append = s -> s + "-appended";
    private final UnaryOperator<String> prepend = s -> "prepended-" + s;

    private final CompositeJsonMasker masker = new CompositeJsonMasker(append, prepend);

    @Test
    void shouldCombineAllOperators() {
        String input = "input";

        String output = masker.apply(input);

        assertThat(output).isEqualTo("prepended-input-appended");
    }

}

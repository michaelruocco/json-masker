package uk.co.mruoc.json.mask;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.UnaryOperator;

@Slf4j
public class CompositeJsonMasker implements UnaryOperator<String> {

    private final UnaryOperator<String> composite;

    @SafeVarargs
    public CompositeJsonMasker(UnaryOperator<String>... maskers) {
        this(Arrays.asList(maskers));
    }

    public CompositeJsonMasker(Collection<UnaryOperator<String>> maskers) {
        this.composite = maskers.stream().reduce(s -> s, (a, b) -> s -> b.apply(a.apply(s)));
    }

    @Override
    public String apply(String json) {
        return composite.apply(json);
    }

}

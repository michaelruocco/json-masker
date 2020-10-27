package uk.co.mruoc.json.mask;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.MapFunction;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.json.mask.string.StringMasker;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class MaskFunction implements MapFunction {

    private final ObjectMasker masker;

    public MaskFunction(char maskChar) {
        this(new StringMasker(maskChar));
    }

    public MaskFunction(UnaryOperator<String> stringMasker) {
        this(new ObjectMasker(stringMasker));
    }

    public MaskFunction() {
        this(new ObjectMasker());
    }

    @Override
    public Object map(Object currentValue, Configuration configuration) {
        return masker.mask(currentValue);
    }

}

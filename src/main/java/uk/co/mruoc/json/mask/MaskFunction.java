package uk.co.mruoc.json.mask;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.MapFunction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MaskFunction implements MapFunction {

    private final ObjectMasker masker;

    public MaskFunction(char maskChar) {
        this(new ObjectMasker(maskChar));
    }

    public MaskFunction() {
        this(new ObjectMasker());
    }

    @Override
    public Object map(Object currentValue, Configuration configuration) {
        return masker.mask(currentValue);
    }

}

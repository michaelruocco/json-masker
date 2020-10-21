package uk.co.mruoc.json.mask;

import com.jayway.jsonpath.Configuration;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MaskFunctionTest {

    private final ObjectMasker masker = mock(ObjectMasker.class);

    private final MaskFunction function = new MaskFunction(masker);

    @Test
    void shouldMaskNullValue() {
        Object value = new Object();
        Object expected = new Object();
        given(masker.mask(value)).willReturn(expected);
        Configuration configuration = mock(Configuration.class);

        Object masked = function.map(value, configuration);

        assertThat(masked).isEqualTo(expected);
    }

}

package uk.co.mruoc.json.mask.phone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.json.mask.JsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;

import java.util.Collection;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

class PhoneNumberJsonMaskerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final Collection<JsonPath> jsonPaths = JsonPathFactory.toJsonPaths("$.[*]");
    private final JsonMasker masker = new PhoneNumberJsonMasker(MAPPER, jsonPaths);

    @Test
    void shouldMaskValuesAtJsonPathAsPhoneNumbers() {
        String json = loadContentFromClasspath("phone/phone-numbers.json");

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson).isEqualTo(loadContentFromClasspath("phone/phone-numbers-masked.json"));
    }

}

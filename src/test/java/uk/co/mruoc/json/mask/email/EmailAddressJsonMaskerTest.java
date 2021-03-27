package uk.co.mruoc.json.mask.email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.json.mask.JsonMasker;
import uk.co.mruoc.json.mask.JsonPathFactory;

import java.util.Collection;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

class EmailAddressJsonMaskerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final Collection<JsonPath> jsonPaths = JsonPathFactory.toJsonPaths("$.[*]");
    private final JsonMasker masker = new EmailAddressJsonMasker(MAPPER, jsonPaths);

    @Test
    void shouldMaskValuesAtJsonPathAsEmailAddress() {
        String json = loadContentFromClasspath("email/emails.json");

        String maskedJson = masker.apply(json);

        assertThatJson(maskedJson).isEqualTo(loadContentFromClasspath("email/emails-masked.json"));
    }

}

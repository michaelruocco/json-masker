package uk.co.mruoc.json.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

class JsonMaskerIntegrationTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String EXAMPLE_JSON = loadContentFromClasspath("example.json");

    @ParameterizedTest(name = "should mask values using path {0}")
    @CsvSource({
            "$.textField1, masked-text-field.json",
            "$.numericField1, masked-numeric-field.json",
            "$.nullField1, masked-null-field.json",
            "$.object1, masked-object.json",
            "$.textArray1, masked-text-array.json",
            "$.numericArray1, masked-numeric-array.json",
            "$.booleanArray1, masked-boolean-array.json",
            "$.objectArray1, masked-object-array.json",
            "$.nestedObject1, masked-nested-object.json"
    })
    void shouldMaskTextField(String jsonPath, String expectedJsonFilePath) {
        JsonMasker masker = buildMaskerWithPaths(jsonPath);

        String masked = masker.mask(EXAMPLE_JSON);

        assertThatJson(masked).whenIgnoringPaths(jsonPath).isEqualTo(EXAMPLE_JSON);
        assertThatJson(masked).inPath(jsonPath).isEqualTo(loadContentFromClasspath(expectedJsonFilePath));
    }

    @Test
    void shouldMaskMultiplePaths() {
        String[] paths = new String[]{"$.textField1","$.numericField1"};
        JsonMasker masker = buildMaskerWithPaths(paths);

        String masked = masker.mask(EXAMPLE_JSON);

        assertThatJson(masked).whenIgnoringPaths(paths).isEqualTo(EXAMPLE_JSON);
        assertThatJson(masked).inPath(paths[0]).isEqualTo(loadContentFromClasspath("masked-text-field.json"));
        assertThatJson(masked).inPath(paths[1]).isEqualTo(loadContentFromClasspath("masked-numeric-field.json"));
    }

    @Test
    void shouldMaskWithCustomMaskCharacter() {
        String path = "$.textField1";
        JsonMasker masker = maskerBuilderWithPaths(path)
                .maskFunction(new MaskFunction('-'))
                .build();

        String masked = masker.mask(EXAMPLE_JSON);

        assertThatJson(masked).whenIgnoringPaths(path).isEqualTo(EXAMPLE_JSON);
        assertThatJson(masked).inPath(path).isEqualTo(loadContentFromClasspath("masked-text-field-with-custom-mask.json"));
    }

    private static JsonMasker buildMaskerWithPaths(String... paths) {
        return maskerBuilderWithPaths(paths).build();
    }

    private static JsonMasker.JsonMaskerBuilder maskerBuilderWithPaths(String... paths) {
        return JsonMasker.builder()
                .paths(JsonPathFactory.toJsonPaths(paths))
                .mapper(MAPPER);
    }

}

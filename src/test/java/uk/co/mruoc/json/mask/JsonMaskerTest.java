package uk.co.mruoc.json.mask;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

class JsonMaskerTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String EXAMPLE_JSON = loadContentFromClasspath("example.json");

    //TODO parametize all tests except multiple paths test and custom mask char test
    @Test
    void shouldMaskTextField() {
        String path = "$.textField1";
        JsonMasker masker = buildMaskerWithPaths(path);

        String masked = masker.mask(EXAMPLE_JSON);

        assertThatJson(masked).whenIgnoringPaths(path).isEqualTo(EXAMPLE_JSON);
        assertThatJson(masked).inPath(path).isEqualTo(loadContentFromClasspath("masked-text-field.json"));
    }

    @Test
    void shouldMaskNumericField() {
        String path = "$.numericField1";
        JsonMasker masker = buildMaskerWithPaths(path);

        String masked = masker.mask(EXAMPLE_JSON);

        assertThatJson(masked).whenIgnoringPaths(path).isEqualTo(EXAMPLE_JSON);
        assertThatJson(masked).inPath(path).isEqualTo(loadContentFromClasspath("masked-numeric-field.json"));
    }

    @Test
    void shouldMaskNullField() {
        String path = "$.nullField1";
        JsonMasker masker = buildMaskerWithPaths(path);

        String masked = masker.mask(EXAMPLE_JSON);

        assertThatJson(masked).whenIgnoringPaths(path).isEqualTo(EXAMPLE_JSON);
        assertThatJson(masked).inPath(path).isEqualTo(loadContentFromClasspath("masked-null-field.json"));
    }

    @Test
    void shouldMaskObject() {
        String path = "$.object1";
        JsonMasker masker = buildMaskerWithPaths(path);

        String masked = masker.mask(EXAMPLE_JSON);

        assertThatJson(masked).whenIgnoringPaths(path).isEqualTo(EXAMPLE_JSON);
        assertThatJson(masked).inPath(path).isEqualTo(loadContentFromClasspath("masked-object.json"));
    }

    @Test
    void shouldMaskTextArray() {
        String path = "$.textArray1";
        JsonMasker masker = buildMaskerWithPaths(path);

        String masked = masker.mask(EXAMPLE_JSON);

        assertThatJson(masked).whenIgnoringPaths(path).isEqualTo(EXAMPLE_JSON);
        assertThatJson(masked).inPath(path).isEqualTo(loadContentFromClasspath("masked-text-array.json"));
    }

    @Test
    void shouldMaskNumericArray() {
        String path = "$.numericArray1";
        JsonMasker masker = buildMaskerWithPaths(path);

        String masked = masker.mask(EXAMPLE_JSON);

        assertThatJson(masked).whenIgnoringPaths(path).isEqualTo(EXAMPLE_JSON);
        assertThatJson(masked).inPath(path).isEqualTo(loadContentFromClasspath("masked-numeric-array.json"));
    }

    @Test
    void shouldMaskNestedObject() {
        String path = "$.nestedObject1";
        JsonMasker masker = buildMaskerWithPaths(path);

        String masked = masker.mask(EXAMPLE_JSON);

        assertThatJson(masked).whenIgnoringPaths(path).isEqualTo(EXAMPLE_JSON);
        assertThatJson(masked).inPath(path).isEqualTo(loadContentFromClasspath("masked-nested-object.json"));
    }

    @Test
    void shouldMaskMultiplePaths() {
        String[] paths = new String[] {"$.textField1", "$.numericField1"};
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

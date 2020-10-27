package uk.co.mruoc.json.mask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.MapFunction;
import org.junit.jupiter.api.Test;

import java.io.UncheckedIOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class JsonMaskerTest {

    private final ObjectMapper mapper = mock(ObjectMapper.class);
    private final JsonPath path1 = mock(JsonPath.class);
    private final JsonPath path2 = mock(JsonPath.class);
    private final MapFunction maskFunction = mock(MapFunction.class);
    private final Configuration jsonPathConfig = mock(Configuration.class);

    private final JsonMasker masker = JsonMasker.builder()
            .mapper(mapper)
            .paths(Arrays.asList(path1, path2))
            .maskFunction(maskFunction)
            .jsonPathConfig(jsonPathConfig)
            .build();

    @Test
    void shouldReturnTextFromCopyOfParsedInput() {
        String json = "{\"field\":\"value\"}";
        JsonNode parsedNode = givenParsedTo(json);
        String expectedJson = "{\"field\":\"*****\"}";
        JsonNode copiedNode = givenDeepCopy(parsedNode);
        given(copiedNode.toString()).willReturn(expectedJson);

        String masked = masker.apply(json);

        assertThat(masked).isEqualTo(expectedJson);
    }

    @Test
    void shouldApplyMaskFunctionForEveryPathConfigured() {
        String json = "{\"field\":\"value\"}";
        JsonNode parsedNode = givenParsedTo(json);
        String expectedJson = "{\"field\":\"*****\"}";
        JsonNode copiedNode = givenDeepCopy(parsedNode);
        given(copiedNode.toString()).willReturn(expectedJson);

        masker.apply(json);

        verify(path1).map(copiedNode, maskFunction, jsonPathConfig);
        verify(path2).map(copiedNode, maskFunction, jsonPathConfig);
    }

    @Test
    void shouldThrowExceptionIfJsonCannotBeParsed() throws JsonProcessingException {
        String json = "{\"field\":\"value\"}";
        JsonProcessingException cause = mock(JsonProcessingException.class);
        given(mapper.readTree(json)).willThrow(cause);

        Throwable error = catchThrowable(() -> masker.apply(json));

        assertThat(error)
                .isInstanceOf(UncheckedIOException.class)
                .hasCause(cause);
    }

    private JsonNode givenParsedTo(String json) {
        try {
            JsonNode parsedNode = mock(JsonNode.class);
            given(mapper.readTree(json)).willReturn(parsedNode);
            return parsedNode;
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    private JsonNode givenDeepCopy(JsonNode node) {
        JsonNode copiedNode = mock(JsonNode.class);
        given(node.deepCopy()).willReturn(copiedNode);
        return copiedNode;
    }

}

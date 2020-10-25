package uk.co.mruoc.json.mask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.MapFunction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.UncheckedIOException;
import java.util.Collection;

@Builder
@Slf4j
@AllArgsConstructor
public class JsonMasker {

    private final ObjectMapper mapper;
    private final Collection<JsonPath> paths;

    @Builder.Default
    private final MapFunction maskFunction = new MaskFunction();

    @Builder.Default
    private final Configuration jsonPathConfig = DefaultJsonPathConfig.build();

    public String mask(String json) {
        try {
            JsonNode input = mapper.readTree(json);
            JsonNode output = mask(input);
            return output.toString();
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    private JsonNode mask(JsonNode target) {
        JsonNode result = target.deepCopy();
        paths.forEach(path -> path.map(result, maskFunction, jsonPathConfig));
        return result;
    }

}

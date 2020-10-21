package uk.co.mruoc.json.mask;

import com.jayway.jsonpath.JsonPath;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public interface JsonPathFactory {

    static Collection<JsonPath> toJsonPaths(String... paths) {
        return toJsonPaths(Arrays.asList(paths));
    }

    static Collection<JsonPath> toJsonPaths(Collection<String> paths) {
        return paths.stream().map(JsonPath::compile).collect(Collectors.toList());
    }

}

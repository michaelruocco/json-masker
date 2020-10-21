package uk.co.mruoc.json.mask;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class JsonPathFactoryTest {

    @Test
    void shouldConvertStringsToJsonPath() {
        String[] paths = new String[] {
                "$.field1",
                "$.object1.field1"
        };

        Collection<JsonPath> jsonPaths = JsonPathFactory.toJsonPaths(paths);

        assertThat(jsonPaths).extracting("path").containsExactly(
                "$['field1']",
                "$['object1']['field1']"
        );
    }

}

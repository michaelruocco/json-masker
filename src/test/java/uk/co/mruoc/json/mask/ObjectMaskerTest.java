package uk.co.mruoc.json.mask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectMaskerTest {

    private final ObjectMasker masker = new ObjectMasker();

    @Test
    void shouldReturnFourMaskCharsIfValueIsNull() {
        Object value = null;

        Object masked = masker.mask(value);

        assertThat(masked).isEqualTo("****");
    }

    @Test
    void shouldMaskStringValue() {
        Object value = "my-string";

        Object masked = masker.mask(value);

        assertThat(masked).isEqualTo("*********");
    }

    @Test
    void shouldMaskTextNodeValue() {
        JsonNode value = new TextNode("my-value");

        Object masked = masker.mask(value);

        assertThat(masked).hasToString("\"********\"");
    }

    @Test
    void shouldMaskAllFieldsOfObjectNode() {
        ObjectNode value = new ObjectNode(JsonNodeFactory.instance);
        value.put("textField1", "value1");
        value.put("numericField1", 1);
        value.put("booleanField", true);

        Object masked = masker.mask(value);

        assertThat(masked).hasToString("{\"textField1\":\"******\",\"numericField1\":\"*\",\"booleanField\":\"****\"}");
    }

    @Test
    void shouldMaskAllValuesOfArrayNode() {
        ArrayNode value = new ArrayNode(JsonNodeFactory.instance);
        value.add("value1");
        value.add(1);
        value.add(true);

        Object masked = masker.mask(value);

        assertThat(masked).hasToString("[\"******\",\"*\",\"****\"]");
    }

}

package uk.co.mruoc.json.mask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class ObjectMasker {

    private static final char DEFAULT_MASK_CHAR = '*';

    private final char maskChar;

    public ObjectMasker() {
        this(DEFAULT_MASK_CHAR);
    }

    public Object mask(Object object) {
        if (object == null) {
            return StringUtils.repeat(maskChar, 4);
        }
        if (object instanceof JsonNode) {
            return maskNode((JsonNode) object);
        }
        return maskString(object.toString());
    }

    private JsonNode maskNode(JsonNode node) {
        if (node instanceof ObjectNode) {
            return maskObjectNode((ObjectNode) node);
        }
        if (node instanceof ArrayNode) {
            return maskArrayNode((ArrayNode) node);
        }
        return new TextNode(maskString(node.asText()));
    }

    private ObjectNode maskObjectNode(ObjectNode node) {
        ObjectNode copy = node.deepCopy();
        node.fields().forEachRemaining(entry -> copy.set(entry.getKey(), maskNode(entry.getValue())));
        return copy;
    }

    private ArrayNode maskArrayNode(ArrayNode node) {
        ArrayNode copy = node.deepCopy();
        for (int i = 0; i < copy.size(); i++) {
            copy.set(i, maskNode(node.get(i)));
        }
        return copy;
    }

    private String maskString(String value) {
        return StringUtils.repeat(maskChar, value.length());
    }

}

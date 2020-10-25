package uk.co.mruoc.json.mask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObjectMasker {

    private final ValueMasker valueMasker;

    public ObjectMasker() {
        this(new DefaultValueMasker());
    }

    public Object mask(Object object) {
        if (object == null) {
            return valueMasker.mask(null);
        }
        if (object instanceof JsonNode) {
            return maskNode((JsonNode) object);
        }
        return valueMasker.mask(object.toString());
    }

    private JsonNode maskNode(JsonNode node) {
        if (node instanceof ObjectNode) {
            return maskObjectNode((ObjectNode) node);
        }
        if (node instanceof ArrayNode) {
            return maskArrayNode((ArrayNode) node);
        }
        return new TextNode(valueMasker.mask(node.asText()));
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

}

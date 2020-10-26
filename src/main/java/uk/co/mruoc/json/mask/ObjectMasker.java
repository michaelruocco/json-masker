package uk.co.mruoc.json.mask;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.RequiredArgsConstructor;
import uk.co.mruoc.json.mask.string.StringMasker;

import java.util.function.UnaryOperator;

@RequiredArgsConstructor
public class ObjectMasker {

    private final UnaryOperator<String> stringMasker;

    public ObjectMasker() {
        this(new StringMasker());
    }

    public Object mask(Object object) {
        if (object == null) {
            return stringMasker.apply(null);
        }
        if (object instanceof JsonNode) {
            return maskNode((JsonNode) object);
        }
        return stringMasker.apply(object.toString());
    }

    private JsonNode maskNode(JsonNode node) {
        if (node instanceof ObjectNode) {
            return maskObjectNode((ObjectNode) node);
        }
        if (node instanceof ArrayNode) {
            return maskArrayNode((ArrayNode) node);
        }
        return new TextNode(stringMasker.apply(node.asText()));
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

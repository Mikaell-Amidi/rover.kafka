package com.nordic.base.context;

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.apache.commons.lang3.StringUtils;

public class JsonNodeFactory {

    public JsonNodeFactory() {
        this.mapper = new ObjectMapper();
        this.node = mapper.createObjectNode();
    }

    public void setNode(String key, String value) {
        this.setJsonPointerValue(node, JsonPointer.compile("/".concat(key)), new TextNode(value));
    }

    public void setParentNode(String parentKey, String key, String value) {
        this.setJsonPointerValue(node, JsonPointer
                .compile("/".concat(parentKey).concat("/").concat(key)), new TextNode(value));
    }

    public void setJsonPointerValue(ObjectNode node, JsonPointer pointer, JsonNode value) {
        JsonPointer parentPointer = pointer.head();
        JsonNode parentNode = node.at(parentPointer);
        String fieldName = pointer.last().toString().substring(1);

        if (parentNode.isMissingNode() || parentNode.isNull()) {
            parentNode = StringUtils.isNumeric(fieldName) ? mapper.createArrayNode() : mapper.createObjectNode();
            setJsonPointerValue(node, parentPointer, parentNode);
        }

        if (parentNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) parentNode;
            int index = Integer.parseInt(fieldName);
            for (int i = arrayNode.size(); i <= index; i++) {
                arrayNode.addNull();
            }
            arrayNode.set(index, value);
        } else if (parentNode.isObject()) {
            ((ObjectNode) parentNode).set(fieldName, value);
        } else {
            throw new IllegalArgumentException("`" + fieldName + "` can't be set for parent node `"
                    + parentPointer + "` because parent is not a container but " + parentNode.getNodeType().name());
        }
    }

    public String extract() {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.node);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private final ObjectNode node;

    private final ObjectMapper mapper;
}

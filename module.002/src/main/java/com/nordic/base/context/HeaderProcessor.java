package com.nordic.base.context;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.util.Arrays;

public class HeaderProcessor implements Transformer<Object, Object, KeyValue<Object, Object>> {
    public HeaderProcessor() {
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
    }

    @Override
    public KeyValue<Object, Object> transform(Object key, Object value) {
        factory.setNode("key", key.toString());
        factory.setNode("message", value.toString());
        Arrays.stream(context.headers().toArray())
                .forEach(header -> factory.setParentNode("header", header.key(), new String(header.value())));
        return new KeyValue<>(key, factory.extract());
    }

    @Override
    public void close() {
    }


    private ProcessorContext context;

    private final JsonNodeFactory factory = new JsonNodeFactory();
}

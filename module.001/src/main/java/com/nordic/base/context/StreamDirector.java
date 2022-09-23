package com.nordic.base.context;

import com.nordic.visitor.Visitor;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;
import java.util.function.Consumer;

public class StreamDirector<T, R> {

    private final Properties properties;

    private final Visitor<Consumer<R>> visitor;

    public StreamDirector(Properties properties, Visitor<Consumer<R>> visitor) {
        this.properties = properties;
        this.visitor = visitor;
    }

    public void operate() {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<T, R> textLines = builder.stream(properties.getProperty("topic"));

        textLines.foreach((u, v) -> visitor.visit(u).accept(v));

        KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}

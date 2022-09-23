package com.nordic.base.context;

import com.nordic.strategy.Strategy;
import com.nordic.strategy.StrategyContext;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;

import java.util.ArrayList;
import java.util.Properties;


public class StreamDirector<K, V> extends StrategyContext<KStream<K, V>> {

    public StreamDirector(Strategy<KStream<K, V>> strategy) {
        super(new ArrayList<>());
        this.properties = properties;
    }

    public StreamDirector<K, V> properties(Properties properties) {
        this.properties = properties;
        return this;
    }

    public StreamDirector<K, V> add(Strategy<KStream<K, V>> strategy) {
        super.strategy().add(strategy);
        return this;
    }

    public void operate() {
        StreamsBuilder builder = new StreamsBuilder();
        super.trigger(builder.stream(properties.getProperty("topic")));
        KafkaStreams streams = new KafkaStreams(builder.build(), properties);
        streams.start();
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    private Properties properties = new Properties();

}

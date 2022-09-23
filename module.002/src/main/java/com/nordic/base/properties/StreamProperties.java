package com.nordic.base.properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

public class StreamProperties {
    private final Properties properties;

    public StreamProperties() {
        this.properties = new Properties();
    }

    public Properties extract(String host, String topic, String offset){
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "module.001");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, host);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offset);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put("topic",topic);
        return properties;
    }
}

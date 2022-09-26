package com.nordic;


import com.nordic.base.context.HeaderProcessor;
import com.nordic.base.context.StreamDirector;
import com.nordic.base.properties.StreamProperties;

import java.util.Objects;

public class DistributedTopology {
    public static void main(String[] args) {

        StreamDirector<Object, Object> strategy = new StreamDirector<>(
                r -> r.filter((u, v) -> Objects.nonNull(u)).filter((u, v) -> u.equals("mail"))
                        .foreach((key, value) -> System.out.println(key + " -> " + value)))
                .add(kStream -> kStream.transform(HeaderProcessor::new)
                        .foreach((key, value) -> System.out.println(value)));

        strategy.properties(new StreamProperties()
                .extract("localhost:9092", "notification", "latest"));

        strategy.operate();
    }
}

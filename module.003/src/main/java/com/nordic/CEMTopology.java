package com.nordic;

import com.nordic.base.context.StreamDirector;
import com.nordic.base.properties.StreamProperties;

import java.util.Objects;

public class CEMTopology {
    public static void main(String[] args) {
        StreamDirector<String, String> director = new StreamDirector<>(
                new StreamProperties()
                        .extract("localhost:9092", "quickstart-events", "latest"));

        director.add(r -> r.filter((u, v) -> Objects.nonNull(u) && u.equals("navax"))
                        .foreach((u, v) -> System.out.println("navax -> " + v)))
                .add(r -> r.filter((u, v) -> Objects.nonNull(u) && u.equals("mail"))
                        .foreach((key, value) -> System.out.println("mail -> " + value)))
                .operate();
    }
}

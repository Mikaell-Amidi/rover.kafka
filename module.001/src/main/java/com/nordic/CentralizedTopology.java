package com.nordic;

import com.nordic.base.context.StreamDirector;
import com.nordic.base.properties.StreamProperties;
import com.nordic.visitor.Visitor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CentralizedTopology {
    public static void main(String[] args) {

        Map<String, Consumer<String>> map = new HashMap<>();
        map.put("mail", System.out::println);
        map.put("navax", o -> System.out.println("the navax event with payload" + o));

        StreamDirector<String, String> stream = new
                StreamDirector<>(new StreamProperties()
                .extract("localhost:9092", "quickstart-events", "latest"),
                Visitor.of(map, System.out::println));

        stream.operate();
    }
}

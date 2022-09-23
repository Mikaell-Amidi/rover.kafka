package com.nordic.bootstrap.base.representation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PartitionControllerImpl implements PartitionController {

    private final KafkaTemplate<String, String> template;

    @Override
    public ResponseEntity<String> createADAutoComplete(String partition) {
        template.send("quickstart-events", partition, "client side message");
        return ResponseEntity.status(HttpStatus.OK).body("sent");
    }
}

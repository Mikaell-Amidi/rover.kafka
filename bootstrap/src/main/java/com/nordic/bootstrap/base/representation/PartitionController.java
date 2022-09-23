package com.nordic.bootstrap.base.representation;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api")
public interface PartitionController {

    @GetMapping("/test")
    ResponseEntity<String> createADAutoComplete(@RequestParam(value = "partition") String partition);
}

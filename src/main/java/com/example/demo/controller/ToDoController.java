package com.example.demo.controller;

import com.example.demo.service.RoundRobinService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class TodoController {

    private final RoundRobinService rrService;
    private final RestTemplate restTemplate = new RestTemplate();

    private final Counter jsonCounter;
    private final Counter dummyCounter;

    public TodoController(RoundRobinService rrService, MeterRegistry registry) {
        this.rrService = rrService;

        jsonCounter = registry.counter("requests_total", "endpoint", "jsonplaceholder");
        dummyCounter = registry.counter("requests_total", "endpoint", "dummyjson");
    }

    @GetMapping("/todos")
    public String getTodos() {
        String url = rrService.getNextUrl();

        if (url.contains("jsonplaceholder")) {
            jsonCounter.increment();
        } else {
            dummyCounter.increment();
        }

        return restTemplate.getForObject(url, String.class);
    }
}

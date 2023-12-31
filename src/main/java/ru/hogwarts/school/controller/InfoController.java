package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("/info")
public class InfoController {
    @Value("${server.port}")
    private Integer port;

    private Logger LOG = LoggerFactory.getLogger(InfoController.class);

    @GetMapping("/port")
    public Integer getPort() {
        return port;
    }

    @GetMapping("/optimized-stream")
    public long getIntPerBestTime () {
        long startingTime = System.currentTimeMillis();
        long sum = Stream.iterate(1, a -> a + 1).limit(1_000_000).reduce(0, (a, b) -> a + b);
        LOG.info(System.currentTimeMillis() - startingTime + " ms");
        long startingTimeOptimized = System.currentTimeMillis();
        long sumOptimized = IntStream.range(1, 1_000_000).parallel().sum();
        LOG.info(System.currentTimeMillis() - startingTimeOptimized + " ms");
        return sumOptimized;
    }
}

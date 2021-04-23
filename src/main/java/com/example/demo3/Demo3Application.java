package com.example.demo3;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.String.*;

@SpringBootApplication
public class Demo3Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo3Application.class, args);
    }

}


@RestController
class HelloController {

    @Autowired
    private Environment environment;

    @GetMapping(value = "/message")
    public Message getMessage() {
        String name = environment.getProperty("HELLO");
        name = name != null ? name : "andrei";
        return new Message(format("hello %s", name));
    }
}

@Data
@RequiredArgsConstructor
class Message {
    private final String message;
}

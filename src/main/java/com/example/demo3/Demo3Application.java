package com.example.demo3;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Demo3Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo3Application.class, args);
	}

}


@RestController
class HelloController{

	@GetMapping(value = "/message")
	public Message getMessage(){
		return new Message("hello");
	}
}

@Data
@RequiredArgsConstructor
class Message{
	private final String message;
}

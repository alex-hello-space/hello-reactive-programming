package org.tutorial.alex.hello.reactive.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserController userApi;


    @Test
    public void testGetById() {
        Mono<User> userMono = userApi.getById(1);
        userMono.subscribe(
                user -> System.out.println(user.toString()),
                error -> System.err.println("Error: " + error.getMessage())
        );
    }

    @Test
    public void testGetAll() {
        Flux<User> userFlux = userApi.getAll();
        userFlux.subscribe(
                user -> System.out.println(user.toString()),
                error -> System.err.println("Error: " + error.getMessage())
        );
    }
}
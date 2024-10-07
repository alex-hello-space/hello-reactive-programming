package org.tutorial.alex.hello.reactive.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.tutorial.alex.hello.reactive.webflux.model.ActionResult;
import reactor.core.publisher.Flux;

public class UserControllerTest {


    @Test
    public void testGetAll() throws InterruptedException {
        // 延迟获取，是否堵塞？
        Flux<ActionResult> userFlux = client.get()
                .uri("/flux/all").retrieve().bodyToFlux(ActionResult.class);

        userFlux.subscribe(
                result -> System.out.println(result.getMessage() + " > " + result.getUserRes()),
                error -> System.err.println("Error: " + error.getMessage())
        );
        Thread.sleep(100000);
    }

    private WebClient client = WebClient.builder().baseUrl("http://localhost:8080").build();
}
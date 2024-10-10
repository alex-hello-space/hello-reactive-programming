package org.tutorial.alex.hello.reactive.webflux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tutorial.alex.hello.reactive.webflux.model.ActionResult;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/flux")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 查询多个
    @GetMapping("/all")
    public ResponseEntity<Flux<ActionResult>> getAll() {
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(userService.getAll());
    }

    @GetMapping("/all2")
    public ResponseEntity<Flux<ActionResult>> getAllWithOutMediaType() {
        return ResponseEntity.ok()
                .body(userService.getAll());
    }
}


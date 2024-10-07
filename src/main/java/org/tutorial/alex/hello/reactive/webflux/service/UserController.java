package org.tutorial.alex.hello.reactive.webflux.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.tutorial.alex.hello.reactive.webflux.model.ActionResult;
import org.tutorial.alex.hello.reactive.webflux.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Flux<ActionResult> getAll() {
        return userService.getAll();
    }
}


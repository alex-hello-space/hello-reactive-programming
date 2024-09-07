package org.tutorial.alex.hello.reactive.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    // 根据id查询
    @GetMapping("/{id}")
    public Mono<User> getById(@PathVariable Integer id) {
        return userService.getById(id);
    }

    // 查询多个
    @GetMapping("/all")
    public Flux<User> getAll() {
        return userService.getAll();
    }

    // 保存
    @PostMapping("/save")
    public Mono<Void> save(@RequestBody Mono<User> userMono) {
        return userService.save(userMono);
    }
}


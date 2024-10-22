package org.tutorial.alex.hello.reactive.webflux.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.tutorial.alex.hello.reactive.webflux.model.ActionResult;
import org.tutorial.alex.hello.reactive.webflux.model.User;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class UserService {

    // 模拟数据库存储
    private Map<Integer, User> map = new HashMap<>();

    public UserService() {
        map.put(1, new User("zhangsan"));
        map.put(2, new User("lisi"));
        map.put(3, new User("wangwu"));
    }

    // 查询多个
    public Flux<ActionResult> getAll() {
        return getAllRes();
    }

    private Flux<ActionResult> getAllRes() {
        return msgFlux("start").concatWith(dataFlux()).concatWith(msgFlux("end"));
    }

    private Flux<ActionResult> msgFlux(String message) {
        log.info("msgFlux: " + message);
        return Flux.just(new ActionResult(message));
    }

    private Flux<ActionResult> dataFlux() {
        log.info("dataFlux: start process users");

        return Flux.fromIterable(map.values()).flatMap(user -> {
            log.info("dataFlux: sleep");
            sleepTreeSecs();
            log.info("dataFlux user: " + user.getName());
            return Flux.just(new ActionResult(user));
        });
    }

    private static void sleepTreeSecs() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        // start user1 user2 user3 end
        UserService userService = new UserService();
        userService.getAll().subscribe(result -> log.info("【client receive】 " + result));
    }
}


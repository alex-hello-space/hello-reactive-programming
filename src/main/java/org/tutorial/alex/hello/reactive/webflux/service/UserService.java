package org.tutorial.alex.hello.reactive.webflux.service;

import org.springframework.stereotype.Service;
import org.tutorial.alex.hello.reactive.webflux.model.ActionResult;
import org.tutorial.alex.hello.reactive.webflux.model.User;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.Map;

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
        System.out.println("msgFlux: " + message);
        return Flux.just(new ActionResult(message));
    }

    private Flux<ActionResult> dataFlux() {
        return Flux.fromIterable(map.values()).flatMap(user -> {
            System.out.println(">>>>>>>>>>>>>>>dataFlux: sleep");
            sleepTreeSecs();
            System.out.println("dataFlux: " + user);
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
}


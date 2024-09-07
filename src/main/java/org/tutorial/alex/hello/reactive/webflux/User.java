package org.tutorial.alex.hello.reactive.webflux;

public class User {

    private String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    // get set 构造器 toString 略
}


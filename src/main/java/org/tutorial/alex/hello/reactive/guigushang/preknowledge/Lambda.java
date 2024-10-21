package org.tutorial.alex.hello.reactive.guigushang.preknowledge;

/**
 * @author yyHuangfu
 * @create 2024/10/21
 */

// 使用 @FunctionalInterface 注解声明 MyInterface 是一个函数式接口。
@FunctionalInterface
interface MyInterface {
    int sum(int a, int b);
}

class MyImplementation implements MyInterface {
    @Override
    public int sum(int a, int b) {
        return a + b;
    }
}

public class Lambda {
    MyInterface myInterface1 = new MyImplementation();
    MyInterface myInterface2 = (a, b) -> a + b;
}

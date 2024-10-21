package org.tutorial.alex.hello.reactive.guigushang.preknowledge;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author yyHuangfu
 * @create 2024/10/21
 */


public class Functions {
    // 内置的function: java.util.function这个包下

    public static void main(String[] args) {
        // 有入参无出参
        BiConsumer<String, Integer> b = (m, n) -> System.out.printf(m + n);
        b.accept("hello", 1);
        // 有入参有出参
        Function<String, Integer> f = a -> Integer.parseInt(a);
        f.apply("1");
        // 无入参无出参
        Runnable r = () -> System.out.println("hello");
        r.run();
        // 无入参有出参
        Supplier<Integer> s = () -> 1;
        s.get();
    }

}

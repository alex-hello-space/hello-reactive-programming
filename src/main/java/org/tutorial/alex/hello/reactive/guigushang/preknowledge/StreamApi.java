package org.tutorial.alex.hello.reactive.guigushang.preknowledge;

import java.util.List;
import java.util.stream.Stream;

/**
 * 最佳实践：凡是for循环处理数据的，一律改成stream流处理。
 *
 * @author yyHuangfu
 * @create 2024/10/21
 */
public class StreamApi {
    // 流的三大操作：1.数据流 2.N个中间操作 3.一个终止操作

    // 1. 创建数据流
    Stream<Integer> stream = Stream.of(1, 2, 3);
    Stream<Integer> constStream = Stream.concat(Stream.of(1, 2, 3), stream);
    Stream<Object> buildStream = Stream.builder().add(1).add(2).add(3).build();
    Stream<Integer> listStream = List.of(1, 2, 3).stream(); // 流不会改变原集合，是深拷贝

    // 2. 中间操作
    // 流是并发的还是不并发？和for有啥区别？
    // 非并发情况下，每个元素完整走完一个流水线才轮到下一个元素，和for一样。一个卡住了就不会继续执行
    private static long getCount() { // 同步，和for一样
        return Stream.of(1, 2, 3).filter(x -> {
            System.out.println(Thread.currentThread());
            System.out.println("filter： " + x);
            return x > 1;
        }).map(x -> {
            System.out.println("map: " + Thread.currentThread());
            return x;
        }).count();
    }

    private static long getCountParallel() { // 并发，和for不一样
        return Stream.of(1, 2, 3).parallel().filter(x -> {
            System.out.println("filter thread: " + Thread.currentThread());
            System.out.println("filter： " + x);
            return x > 1;
        }).flatMap(x -> {
            System.out.println("flatMap thread: " + Thread.currentThread());
            System.out.println("flatmap： " + x);
            return Stream.empty();
        }).count();
    }

    // map和flatMap区别：map返回一个流，flatMap返回一个元素，所以flatMap适合处理嵌套的流。
    // map: 映射a->b, mapToInt, mapToLong, mapToDouble
    // flatMap：打散、扩维、展开，一对多映射
    private static void flatMap() {
        Stream.of(1, 2, 3)
                .filter(x -> x > 1)
                .map(x -> x + "plus map")
                .flatMap(x -> Stream.of(x, x + " flat"))
                .forEach(System.out::println);
    }


    public static void main(String[] args) {
        flatMap();
    }
}


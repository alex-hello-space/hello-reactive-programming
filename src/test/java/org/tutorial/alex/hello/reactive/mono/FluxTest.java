package org.tutorial.alex.hello.reactive.mono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class FluxTest {

    Integer[] array1 = {1, 2, 3};
    Integer[] array2 = {4, 5, 6};
    Flux<Integer> integerFlux1 = Flux.just(1, 2, 3);
    Flux<Integer> integerFlux2 = Flux.just(4, 5, 6);

    @Test
    public void testFrom() {
        // fromArray
        Flux.fromArray(array1).subscribe(System.out::print);
        System.out.println("\n");
        // fromIterable
        List<Integer> integers = Arrays.asList(array1);
        Flux.fromIterable(integers).subscribe(System.out::print);
        System.out.println("\n");
        // fromStream
        Stream<Integer> stream = integers.stream();
        Flux.fromStream(stream).subscribe(System.out::print);
        System.out.println("\n");
    }

    @Test
    public void testConcatWith() {
        // 123456 按顺序串联两个事件流
        integerFlux1.concatWith(integerFlux2).subscribe(System.out::print);
        System.out.println("\n");
        Flux.concat(integerFlux1, integerFlux2).subscribe(System.out::print);
    }

    @Test
    public void testMerge() {
        // 123456 并行处理两个事件流中的事件，不保证事件的处理顺序。
        integerFlux1.mergeWith(integerFlux2).subscribe(System.out::print);
        System.out.println("\n");
        Flux.merge(integerFlux1, integerFlux2).subscribe(System.out::print);
    }

    @Test
    public void testZip() {
        // 579
        Flux.zip(integerFlux1, integerFlux2, (a, b) -> a + b).subscribe(System.out::print);
    }

    @Test
    public void testMapMany(){
        // 234
        integerFlux1.flatMap(i -> Flux.just(i + 1)).subscribe(System.out::print);
    }

}
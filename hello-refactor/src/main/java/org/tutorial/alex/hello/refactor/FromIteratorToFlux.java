package org.tutorial.alex.hello.refactor;

import java.util.*;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yyHuangfu
 * @create 2025/8/3
 * <p>
 * 对比Iterable-Iterator和Publisher-Subscriber模式的演示
 */
public class FromIteratorToFlux {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个数据源
        List<String> dataList = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4", "Item 5");

        System.out.println("=== Iterable-Iterator (Pull-based) ===");
        iterableIteratorDemo(dataList);

        System.out.println("\n=== Publisher-Subscriber (Push-based) ===");
        publisherSubscriberDemo(dataList);
    }

    /**
     * 演示传统的Iterable-Iterator模式（拉模式）
     * 在这种模式中，消费者主动从数据源拉取数据
     */
    private static void iterableIteratorDemo(List<String> dataList) {

        // 使用Iterable-Iterator模式遍历数据
        // 消费者主动调用next()方法来获取数据
        java.util.Iterator<String> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            System.out.println("Received: " + item);

            // 模拟处理时间
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    /**
     * 演示Publisher-Subscriber模式（推模式）
     * 在这种模式中，数据源主动将数据推送给订阅者
     */
    private static void publisherSubscriberDemo(List<String> dataList) throws InterruptedException {
        // 创建一个简单的Publisher
        SimplePublisher publisher = new SimplePublisher();

        // 创建一个Subscriber
        SimpleSubscriber subscriber = new SimpleSubscriber("subscriber");

        // 订阅
        publisher.subscribe(subscriber);

        // 发布数据
        publisher.publishData(dataList);

        // 等待处理完成
        Thread.sleep(3000);

        // 完成发布
        publisher.complete();

        // 等待完成信号处理
        Thread.sleep(1000);
    }

    /**
     * 简单的Publisher实现
     */
    static class SimplePublisher implements Flow.Publisher<String> {
        private final Set<SimpleSubscriber> subscribers = new HashSet<>();
        private boolean isCompleted = false;
        private Exception error = null;

        @Override
        public void subscribe(Flow.Subscriber<? super String> subscriber) {
            SimpleSubscriber s = (SimpleSubscriber) subscriber;
            subscribers.add(s);
            // 立即触发订阅
            subscriber.onSubscribe(new SimpleSubscription(s));
        }

        public void publishData(List<String> data) {
            if (isCompleted || error != null) {
                return;
            }

            for (String item : data) {
                final String itemCopy = item;
                subscribers.forEach(
                        subscriber -> subscriber.onNext(itemCopy)
                );

                // 模拟处理延迟
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        public void complete() {
            isCompleted = true;
            subscribers.forEach(SimpleSubscriber::onComplete);
        }

        public void error(Exception e) {
            error = e;
            subscribers.forEach(subscriber -> subscriber.onError(e));
        }
    }

    /**
     * 简单的Subscriber实现
     */
    static class SimpleSubscriber implements Flow.Subscriber<String> {
        private final String name;
        private Flow.Subscription subscription;

        public SimpleSubscriber(String name) {
            this.name = name;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            System.out.println(name + " subscribed.");
            // 请求无限数量的数据项
            subscription.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(String item) {
            System.out.println(name + " onNext: " + item);
        }

        @Override
        public void onError(Throwable throwable) {
            System.err.println(name + " onError: " + throwable.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println(name + " onComplete.");
        }
    }

    /**
     * 简单的Subscription实现
     */
    static class SimpleSubscription implements Flow.Subscription {
        private final SimpleSubscriber subscriber;
        private final AtomicInteger requested = new AtomicInteger(0);

        public SimpleSubscription(SimpleSubscriber subscriber) {
            this.subscriber = subscriber;
        }

        @Override
        public void request(long n) {
            if (n > 0) {
                requested.addAndGet((int) n);
            }
        }

        @Override
        public void cancel() {
            requested.set(0);
        }
    }
}
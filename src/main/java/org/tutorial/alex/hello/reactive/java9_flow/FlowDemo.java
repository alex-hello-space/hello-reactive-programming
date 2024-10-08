package org.tutorial.alex.hello.reactive.java9_flow;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

public class FlowDemo {

    public static void main(String[] args) throws Exception {
        // 1. 定义发布者, 发布的数据类型是 Integer
        // 直接使用jdk自带的SubmissionPublisher, 它实现了 Publisher 接口
        SubmissionPublisher<Integer> publiser = new SubmissionPublisher<>();

        // 2. 定义订阅者
        MyIntSubscriber subscriber = new MyIntSubscriber();

        // 3. 发布者和订阅者 建立订阅关系
        publiser.subscribe(subscriber);

        // 4. 生产数据, 并发布
        // 这里忽略数据生产过程
        for (int i = 1; i <= 15; i++) {
            System.out.println("生成数据:" + i);
            // submit是个block方法
            publiser.submit(i);
        }

        // 5. 结束后 关闭发布者
        // 正式环境 应该放 finally 或者使用 try-resouce 确保关闭
        publiser.close();

        // 使用 CountDownLatch 确保主线程等待订阅者处理完毕
        subscriber.getCountDownLatch().await();
    }

}

class MyIntSubscriber implements Subscriber<Integer> {

    private Subscription subscription;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void onSubscribe(Subscription subscription) {
        // 保存订阅关系, 需要用它来给发布者响应
        this.subscription = subscription;

        // 请求一个数据
        this.subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        // 接受到一个数据, 处理
        System.out.println("接受到数据: " + item);

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 处理完调用request再请求一个数据
        this.subscription.request(1);

        // 或者 已经达到了目标, 调用cancel告诉发布者不再接受数据了
        // this.subscription.cancel();
    }

    @Override
    public void onError(Throwable throwable) {
        // 出现了异常(例如处理数据的时候产生了异常)
        throwable.printStackTrace();
        // 我们可以告诉发布者, 后面不接受数据了
        this.subscription.cancel();
        countDownLatch.countDown();
    }

    @Override
    public void onComplete() {
        // 全部数据处理完了(发布者关闭了)
        System.out.println("处理完了!");
        countDownLatch.countDown();
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

}

package org.tutorial.alex.hello.reactive.java8_observer;

import java.util.Observable;
import java.util.Observer;

/**
 * @author yyHuangfu
 * @create 2024/9/7
 */
public class Java8ReactiveDemo {
    public static void main(String[] args) {
        ObserverDemo observer = new ObserverDemo();
        // 添加观察者
        observer.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("observer1: 发生了变化");
            }
        });
        observer.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("observer2: 收到了通知");
            }
        });
        observer.setChanged(); // 数据变化
        observer.notifyObservers(); // 通知

    }
}

class ObserverDemo extends Observable {
    public void setChanged() {
        super.setChanged();
    }
}

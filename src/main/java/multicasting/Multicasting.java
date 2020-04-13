package multicasting;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Multicasting {
    public static void main(String[] args) {
        Examples examples = new Examples();
        //examples.connectableObservable();
        //examples.operatorMulticastingExample();
        //examples.autoConnectExample();
        examples.refCountExample();
    }
}

class Examples {
    void connectableObservable() {
        ConnectableObservable<Integer> integerObservable = ConnectableObservable.range(1, 3).publish();
        integerObservable.subscribe(i -> System.out.println("Source 1:" + i));
        integerObservable.subscribe(i -> System.out.println("Source 2:" + i));
        integerObservable.connect();
    }

    int getRandomNumbers() {
        return ThreadLocalRandom.current().nextInt(100000);
    }

    void delay(){
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void operatorMulticastingExample() {
        ConnectableObservable<Integer> integerConnectableObservable =
                ConnectableObservable.range(1, 5)
                        .map(i -> getRandomNumbers())
                        .publish();
        integerConnectableObservable
                .subscribe(s -> System.out.println("Source 1: " + s));
        integerConnectableObservable
                .subscribe(s -> System.out.println("Source 2: " + s));
        integerConnectableObservable
                .connect();
    }

    void autoConnectExample(){
        Observable<Integer> integerObservable = Observable.range(1,3)
                .map(i->getRandomNumbers())
                .publish()
                .autoConnect(2);
                //.autoConnect(3);
                //.autoConnect();
        integerObservable
                .subscribe(s -> System.out.println("Observer 1: " + s));
        integerObservable
                .reduce(Integer::sum)
                .subscribe(s -> System.out.println("Observer 2: " + s));
        //integerObservable.subscribe(i -> System.out.println("Observer 3: " + i));
    }

    void refCountExample(){
        /*Observable<Long> longObservable = Observable.interval(1, TimeUnit.SECONDS).publish().refCount();
        longObservable.take(5).subscribe(s->System.out.println("Observable 1: "+s));
        delay();
        longObservable.take(2).subscribe(s->System.out.println("Observable 2: "+s));
        delay();
        longObservable.subscribe(s->System.out.println("Observable 3: "+s));
        delay();*/
        Observable<Long> longObservable = Observable.interval(1, TimeUnit.SECONDS).share();
        longObservable.take(5).subscribe(s->System.out.println("Observable 1: "+s));
        delay();
        longObservable.take(2).subscribe(s->System.out.println("Observable 2: "+s));
        delay();
        longObservable.subscribe(s->System.out.println("Observable 3: "+s));
        delay();
    }
}

/*
The Observable.create() factory allows us to create an Observable by providing a lambda receiving an Observable emitter.
We can call the Observable emitter's onNext() method to pass emissions (one a time) up the chain as well as onComplete()
to signal completion and communicate that there will be no more items.
*/

import io.reactivex.*;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.ResourceObserver;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

class Introduction {
    public static void main(String[] args) {
        Examples examples = new Examples();
        //examples.firstExample();
        //examples.secondExample();
        //examples.thirdExample();
        //examples.fourthExample();
        //examples.fifthExample();
        //examples.sixthExample();
        //examples.seventhExample();
        //examples.eightExample();
        //examples.ninthExample();
        //examples.tenthExample();
        //examples.eleventhExample();
        //examples.twelfthExample();
        //examples.singleExample();
        //examples.maybeExample();
        //examples.completableExample();
        //examples.disposeExample();
        //examples.resourceObserverExample();
        examples.compositeDisposableExample();
    }
}


class Examples {
    static void Log(String data) {
        System.out.println(data);
    }

    void firstExample() {
        Log("firstExample");
        Observable<String> source = Observable.create(emitter ->
                {
                    emitter.onNext("1");
                    emitter.onNext("2");
                    emitter.onNext("3");
                    emitter.onNext("4");
                    emitter.onNext("5");
                    emitter.onNext("6");
                    emitter.onNext("7");
                    emitter.onNext("8");
                    emitter.onNext("9");
                    emitter.onNext("10");
                    emitter.onComplete();
                }
        );
        source.subscribe(data -> System.out.println(data));
    }

    void secondExample() {
        Log("secondExample");
        Observable<String> source = Observable.create(emitter ->
                {
                    emitter.onNext("one");
                    emitter.onNext("two");
                    emitter.onNext("three");
                    emitter.onNext("four");
                    emitter.onNext("five");
                    emitter.onNext("six");
                    emitter.onNext("seven");
                    emitter.onNext("eight");
                    emitter.onNext("nine");
                    emitter.onNext("ten");
                    emitter.onComplete();
                }
        );
        source.filter(dataLength -> dataLength.length() == 3).subscribe(System.out::println);
    }

    void thirdExample() {
        Log("thirdExample");
        Observable<String> source = Observable.just("one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine",
                "ten");
        source.filter(dataLength -> dataLength.length() == 3).subscribe(System.out::println);
    }

    void fourthExample() {
        Log("fourthExample");
        List<String> stringList = Arrays.asList("one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine",
                "ten");
        Observable<String> stringObservable = Observable.fromIterable(stringList);
        stringObservable.filter(dataLength -> dataLength.length() == 3).subscribe(System.out::println);
    }

    void fifthExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        Observable<String> source = Observable.just("one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine",
                "ten");
        Observer<String> myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String value) {
                System.out.println("RECEIVED: " + value);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Done!");
            }
        };
        source.filter(dataLength -> dataLength.length() == 3).subscribe(myObserver);
    }

    void sixthExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        Observable<String> source = Observable.just("one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine",
                "ten");
        source.filter(it -> it.length() == 4).subscribe(data -> System.out.println("Received: " + data), Throwable::printStackTrace, () -> Log("completed"));
    }

    void seventhExample() {
        //When the source is same but different actions to be performed at parallel, we use ConnectableObservable, publish, connect.
        /*
        A helpful form of hot Observable is ConnectableObservable. It will take any Observable, even if it is cold, and make it hot so that all emissions are played to all Observers at once. To do this conversion, you simply need to call publish() on any Observable, and it will yield a ConnectableObservable. But subscribing will not start the emissions yet. You need to call its connect() method to start firing the emissions.
         */
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        ConnectableObservable<String> source = Observable.just("one",
                "two",
                "three",
                "four",
                "five",
                "six",
                "seven",
                "eight",
                "nine",
                "ten").publish();
        source.subscribe(System.out::println);
        source.subscribe(i -> System.out.println(i.length() == 5));
        source.connect();
    }

    void eightExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        Observable<Integer> integerObservable = Observable.range(1, 10);
        integerObservable.subscribe(i -> System.out.println("Received: " + i), Throwable::printStackTrace, () -> Log("Completed"));
    }

    void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void ninthExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        Observable<Long> integerObservable = Observable.interval(1, TimeUnit.SECONDS);
        integerObservable.subscribe(i -> System.out.println(i + " seconds"));
        sleep();
    }

    void tenthExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        Observable<Long> longObservable1 = Observable.interval(1, TimeUnit.SECONDS);
        longObservable1.subscribe(i -> System.out.println(i + " seconds OB1"));
        sleep();
        longObservable1.subscribe(i -> System.out.println(i + " seconds OB2"));
        sleep();
    }

    void eleventhExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        ConnectableObservable<Long> longObservable1 = Observable.interval(1, TimeUnit.SECONDS).publish();
        longObservable1.subscribe(i -> System.out.println(i + " seconds OB1"));
        longObservable1.connect(); //Fire the data
        sleep();
        longObservable1.subscribe(i -> System.out.println(i + " seconds OB2"));
        sleep();

    }

    void twelfthExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        Observable.fromCallable(() -> 1 / 0).subscribe(System.out::println, e -> System.out.println("Mathematical Failure: " + e.getMessage()), () -> Log("Completed"));
    }

    void singleExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        /*Single.just("Hello world").map(String::length)
                .subscribe(System.out::println, e->System.out.println(e.getMessage()));*/

        Observable<String> stringObservable = Observable.just("A", "B", "C", "D", "E");
        stringObservable.last("").subscribe(System.out::println);

        Single<String> singleObservable = Single.just("A");
        singleObservable.subscribe(System.out::println);
    }

    void maybeExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        Maybe<String> stringMaybe1 = Maybe.just("A");
        stringMaybe1.subscribe(System.out::println, e -> Log(e.getMessage()), () -> Log("P1 Completed"));
        Maybe<String> stringMaybe2 = Maybe.empty();
        stringMaybe2.subscribe(data -> System.out.println("OM2: " + data), e -> Log(e.getMessage()), () -> Log("P2 Completed"));
    }

    void completableExample() {
        //Completable is simply concerned with an action being executed, but it does not receive any emissions.
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        Completable.fromRunnable(() -> System.out.println("nothing")).subscribe(() -> System.out.println("Done!"));
    }

    void disposeExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        Observable<Long> longObservable = Observable.interval(1, TimeUnit.SECONDS);
        Disposable disposable = longObservable.subscribe(System.out::println);
        sleep();
        disposable.dispose();
        sleep();
    }

    void resourceObserverExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        Observable<Long> longObservable = Observable.interval(1, TimeUnit.SECONDS);
        ResourceObserver<Long> longResourceObserver = new ResourceObserver<Long>() {
            @Override
            public void onNext(Long aLong) {
                System.out.println(aLong);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                Log("Completed");
            }
        };
        Disposable disposable = longObservable.subscribeWith(longResourceObserver);
        sleep();
        disposable.dispose();
    }

    void compositeDisposableExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        ConnectableObservable<Long> longObservable = ConnectableObservable.interval(1, TimeUnit.SECONDS).publish();
        Disposable disposable1 = longObservable.subscribe(i -> System.out.println("OB1: " + i), e -> Log(e.getMessage()), () -> Log("Completed"));
        longObservable.connect();
        compositeDisposable.add(disposable1);
        sleep();
        Disposable disposable2 = longObservable.subscribe(i -> System.out.println("OB2: " + i), e -> Log(e.getMessage()), () -> Log("Completed"));
        compositeDisposable.add(disposable2);
        sleep();
        compositeDisposable.dispose();
        sleep();
    }
}
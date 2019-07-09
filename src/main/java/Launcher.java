import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Launcher {
    public static void main(String args[]){

        Observable.interval(1,TimeUnit.SECONDS)
                .subscribe(System.out::println);

        sleepNow(5000);
        /*List<String> items =
                Arrays.asList("Alpha", "Beta", "Gamma", "Delta",
                        "Epsilon","sdfsdfsd","rewrwer","32432423","asfdasdas","asdasdasd","sdfsdfsdfsdf","sdfsdfsdfsdfsdfsdfsdf","sdfsdf");*/

        //Observable<String> myStrings = Observable.fromIterable(items);
        //Observable.range(1,items.size()).subscribe(System.out::println);

        /*ConnectableObservable<String> myStrings = Observable.fromIterable(items).publish();

        myStrings.map(String::length).filter(i->i>5).subscribe((System.out::println),
                Throwable::printStackTrace);
        myStrings.subscribe(s->System.out.println(s));

        myStrings.connect();*/

        //myStrings.subscribe(System.out::println);
        //myStrings.map(String::length).subscribe(System.out::println);;
        /*myStrings.map(String::length)
                .filter(inn->inn>=5)
                .subscribe(s->System.out.println("Received: "+s));*/
        /*Observer<Integer> integerObserver = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("Received: "+integer);

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };
        myStrings.map(String::length).filter(i->i>5).subscribe(integerObserver);*/
        /*myStrings.map(String::length).filter(i->i>5).subscribe((System.out::println),
                Throwable::printStackTrace,
                ()->System.out.println("Completed"));*/
        //myStrings.map(String::length).filter(i->i>5).subscribe((System.out::println));
    }

    private static void sleepNow(int milliSesonds){
        try {
            Thread.sleep(milliSesonds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

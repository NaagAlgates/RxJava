package basicoperators;

import io.reactivex.Observable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

class BasicOperators {
    public static void main(String[] args) {
        Examples examples = new Examples();
        //examples.filterExample();
        //examples.takeExample();
        //examples.skipExample();
        //examples.takeWhileExample();
        //examples.skipWhileExample();
        //examples.distinctExample();
        //examples.distinctUntilChangedExample();
        //examples.elementAtExample();
        examples.mapExample();
    }
}


class Examples {
    static void Log(String data) {
        System.out.println(data);
    }
    void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Suppressing Operators
    void filterExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        Observable.just("Lattice", "CellCast", "Nuance", "UST", "Bahwan")
                .filter(s -> s.length() == 3).subscribe(s -> System.out.println("RECEIVED: " + s));
    }
    void takeExample() {
        Log(new Throwable()
                .getStackTrace()[0]
                .getMethodName());
        /*Observable.just(1,2,3,4,5,67,8)
                .take(4)
                .subscribe(System.out::println);*/

        Observable.interval(1, TimeUnit.SECONDS)
                .take(2, TimeUnit.SECONDS)
                .subscribe(System.out::println);
        sleep();
    }
    void skipExample(){
        Observable<String> stringObservable = Observable.just("A","B","C","D","E");
        stringObservable.skip(2).subscribe(System.out::println);
    }
    void takeWhileExample(){
        Observable<Integer> integerObservable = Observable.just(1,2,3,4,5,6,7,8,9);
        integerObservable.takeWhile(i->i<5).subscribe(System.out::println);
    }
    void skipWhileExample(){
        Observable<Integer> integerObservable = Observable.just(1,2,3,4,5,6,7,8,9);
        integerObservable.skipWhile(i->i<5).subscribe(System.out::println);
    }
    void distinctExample(){
        Observable<Integer> integerObservable = Observable.just(1,1,2,2,2,3,2,2,4,4);
        integerObservable.distinct().subscribe(System.out::println);
    }
    void distinctUntilChangedExample(){
        Observable<Integer> integerObservable = Observable.just(1,1,2,2,2,3,2,2,4,4);
        integerObservable.distinctUntilChanged().subscribe(System.out::println);
    }
    void elementAtExample(){
        Observable<Integer> integerObservable = Observable.just(1,2,3,4,5,6,7,8);
        integerObservable.elementAt(4).subscribe(System.out::println);
    }

    //Transforming Operators
    void mapExample(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
        Observable<String> stringObservable = Observable.just("1/3/2016", "5/9/2016", "10/12/2016");
        stringObservable.map(s-> LocalDate.parse(s, dtf)).subscribe(System.out::println,e->Log(e.getMessage()));
    }
}

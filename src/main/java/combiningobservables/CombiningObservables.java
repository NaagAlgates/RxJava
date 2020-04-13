package combiningobservables;

import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class CombiningObservables {
    public static void main(String[] args) {
        Examples examples = new Examples();
        //examples.mergingExample();
        //examples.flatMapExample();
        //examples.concatExample();
        //examples.concatMapExample();
        //examples.ambiguousExample();
        //examples.zippingExample();
        //examples.combineLatestExample();
        //examples.withLatestFrom();
        examples.groupByExample();
    }
}

class Examples {
    void mergingExample() {
        //Observable.merge() will combine multiple Observable<T> sources emitting the same type T and consolidate into a single Observable<T>. It works on infinite Observables and does not necessarily guarantee that the emissions come in any order
        Observable<String> source1 = Observable.just("a", "b", "c", "d", "e");
        Observable<String> source2 = Observable.just("f", "g", "h", "i", "j");
        Observable<String> source3 = Observable.just("k", "l", "m", "n", "o");
        Observable<String> source4 = Observable.just("p", "q", "r", "s", "t");
        Observable<String> source5 = Observable.just("u", "v", "w", "x", "y");
        Observable<String> source6 = Observable.just("z", "aa", "ab", "ac", "ad");
        Observable<String> source7 = Observable.just("ae", "af", "ag", "ah", "ai");
        Observable.merge(source1, source2).subscribe(i -> System.out.println("merge: " + i));
        source3.mergeWith(source4).subscribe(i -> System.out.println("MergeWith: " + i));
        Observable.mergeArray(source5, source6, source7).subscribe(i -> System.out.println("mergeArray: " + i));

        Observable<String> source11 = Observable.interval(1, TimeUnit.SECONDS).take(6)
                .map(l -> l + 1).map(l -> "Source1: " + l + " seconds");
        Observable<String> source12 =
                Observable.interval(300, TimeUnit.MILLISECONDS)
                        .map(l -> (l + 1) * 300).map(l -> "Source2: " + l + " milliseconds");
        Observable.merge(source11, source12)
                .subscribe(i -> System.out.println("RECEIVED: " + i));
        try {
            sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void flatMapExample() {
        /*Observable.just("Alpha","Beta","Gamma","Delta","Epsilon")
                .flatMap(s->Observable.fromArray(s.split("")))
                .subscribe(System.out::println);*/

        Observable.just("521934/2342/FOXTROT", "21962/12112/78886 /TANGO", "283242/4542/WHISKEY/2348562")
                .flatMap(s -> Observable.fromArray(s.split("/")))
                .filter(i -> i.matches("[0-9]+"))
                .map(i -> Integer.parseInt(i) + 1)
                .subscribe(System.out::println, e -> System.out.println("Error: " + e.getMessage()));
    }

    void concatExample() {
        Observable<String> source1 = Observable.interval(1, TimeUnit.SECONDS).take(6)
                .map(l -> l + 1).map(l -> "Source1: " + l + " seconds");
        Observable<String> source2 =
                Observable.interval(300, TimeUnit.MILLISECONDS)
                        .map(l -> (l + 1) * 300).map(l -> "Source2: " + l + " milliseconds");
        Observable.concat(source1, source2)
                .subscribe(i -> System.out.println("RECEIVED: " + i));
        try {
            sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void concatMapExample() {
        /*Observable<String> source =
                Observable.just("Alpha", "Beta", "Gamma", "Delta",
                        "Epsilon");
        source.concatMap(s -> Observable.fromArray(s.split("")))
                .subscribe(System.out::println);*/

        Observable.just("521934/2342/FOXTROT", "21962/12112/78886 /TANGO", "283242/4542/WHISKEY/2348562")
                .concatMap(s -> Observable.fromArray(s.split("/")))
                .filter(i -> i.matches("[0-9]+"))
                .map(i -> Integer.parseInt(i) + 1)
                .subscribe(System.out::println, e -> System.out.println("Error: " + e.getMessage()));
    }

    void ambiguousExample() {
        Observable<String> source1 = Observable.interval(1, TimeUnit.SECONDS).take(4)
                .map(i -> i + " seconds")
                .map(i -> "Source 1: " + i);
        Observable<String> source2 = Observable.interval(300, TimeUnit.MILLISECONDS)
                .map(i -> i * 300 + " milliseconds")
                .map(i -> "Source 2: " + i);
        Observable.amb(Arrays.asList(source1, source2)).subscribe(System.out::println);
        try {
            sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void zippingExample() {
        //Zipping allows you to take an emission from each Observable source and combine it into a single emission. Each Observable can emit a different type, but you can combine these different emitted types into a single emission.
        /*Observable<String> stringObservable = Observable.just("Alpha","Beta","Gamma","Delta");
        Observable<Integer> integerObservable = Observable.range(2,5);
        Observable.zip(stringObservable,integerObservable,(s,i)->s+"-"+i).subscribe(System.out::println);*/

        Observable<String> strings = Observable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon");
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS);
        Observable.zip(strings, seconds, (s, l) -> s).subscribe(s ->
                System.out.println("Received " + s + " at " + LocalTime.now()));
        try {
            sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void combineLatestExample(){
        //In simpler terms, when one source fires, it couples with the latest emissions from the others. Observable.combineLatest() is especially helpful in combining UI inputs, as previous user inputs are frequently irrelevant and only the latest is of concern.
        Observable<Long> strings =Observable.interval(300, TimeUnit.MILLISECONDS);
        Observable<Long> seconds = Observable.interval(1, TimeUnit.SECONDS);
        Observable.combineLatest(strings,seconds, (s,l)-> "Source: "+s+" Seconds: "+l)
                .subscribe(System.out::println);
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void withLatestFrom(){
        Observable<Long> slowSeconds = Observable.interval(300,TimeUnit.MILLISECONDS);
        Observable<Long> fastSeconds = Observable.interval(1,TimeUnit.SECONDS);
        slowSeconds.withLatestFrom(fastSeconds, (a,b) -> "Source: "+a+" Seconds: "+b)
                .subscribe(System.out::println);
        try {
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void groupByExample(){
        Observable<Integer> integerObservable = Observable.just(100,300,200,80,23,99,1009,1234,1000000);
        Observable<GroupedObservable<Integer,Integer>> groupedObservableObservable = integerObservable.groupBy(i->i%10);
        groupedObservableObservable.flatMapSingle(Observable::toList)
                .subscribe(System.out::println);
    }
}
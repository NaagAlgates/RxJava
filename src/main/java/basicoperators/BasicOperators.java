package basicoperators;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;
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
        //examples.mapExample();
        //examples.castExample();
        //examples.startWithExample();
        //examples.defaultIfEmptyExample();
        //examples.switchIfEmptyExample();
        //examples.sortedExample();
        //examples.delayExample();
        //examples.repeatExample();
        //examples.scanExample();
        //examples.countExample();
        //examples.reduceExample();
        //examples.allExample();
        //examples.anyExample();
        //examples.containsExample();
        //examples.listExample();
        //examples.sortedList();
        //examples.toMapExample();
        //examples.collectExample();
        //examples.onErrorReturnItemExample();
        //examples.onErrorReturnExample();
        //examples.onErrorResumeNextExample();
        //examples.retryExample();
        examples.actionOperatorsExample();
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

    void skipExample() {
        Observable<String> stringObservable = Observable.just("A", "B", "C", "D", "E");
        stringObservable.skip(2).subscribe(System.out::println);
    }

    void takeWhileExample() {
        Observable<Integer> integerObservable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9);
        integerObservable.takeWhile(i -> i < 5).subscribe(System.out::println);
    }

    void skipWhileExample() {
        Observable<Integer> integerObservable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9);
        integerObservable.skipWhile(i -> i < 5).subscribe(System.out::println);
    }

    void distinctExample() {
        Observable<Integer> integerObservable = Observable.just(1, 1, 2, 2, 2, 3, 2, 2, 4, 4);
        integerObservable.distinct().subscribe(System.out::println);
    }

    void distinctUntilChangedExample() {
        Observable<Integer> integerObservable = Observable.just(1, 1, 2, 2, 2, 3, 2, 2, 4, 4);
        integerObservable.distinctUntilChanged().subscribe(System.out::println);
    }

    void elementAtExample() {
        Observable<Integer> integerObservable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8);
        integerObservable.elementAt(4).subscribe(System.out::println);
    }

    //Transforming Operators
    void mapExample() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy");
        Observable<String> stringObservable = Observable.just("1/3/2016", "5/9/2016", "10/12/2016");
        stringObservable.map(s -> LocalDate.parse(s, dtf)).subscribe(System.out::println, e -> Log(e.getMessage()));
    }

    void castExample() {
        Observable<String> stringObservable = Observable.just("1", "2", "3", "4", "5");
        stringObservable.map(s -> Integer.parseInt(s) + 1).subscribe(System.out::println);
    }

    void startWithExample() {
        Observable<String> stringObservable = Observable.just("Coffee", "Tea", "Hot Chocolate");
        stringObservable.startWith("Today's Special").subscribe(System.out::println);
        stringObservable.startWithArray("Today's Special", "------------------------").subscribe(System.out::println);
    }

    void defaultIfEmptyExample() {
        Observable<String> stringObservable = Observable.just("Nag", "Abc", "Def", "Ghi", "Mno", "pqr");
        stringObservable.filter(s -> s.startsWith("N"))
                .defaultIfEmpty("NA")
                .subscribe(System.out::println);
    }

    void switchIfEmptyExample() {
        Observable<String> stringObservable = Observable.just("Nag", "Abc", "Def", "Ghi", "Mno", "pqr");
        stringObservable.filter(s -> s.length() > 3)
                .switchIfEmpty(Observable.just("aaaa", "bbbbb", "cccccc"))
                .subscribe(System.out::println);
    }

    void sortedExample() {
        Observable<Integer> stringObservable = Observable.just(1, 2, 6, 8, 3, 5, 0, 5, 2, 8);
        //stringObservable.sorted().subscribe(System.out::println);
        stringObservable.sorted(Comparator.reverseOrder()).subscribe(System.out::println);
        Observable.just("aaa", "bbbbbb", "c", "dd", "eeeeeeeeeeee").sorted(String::lastIndexOf)
                .subscribe(System.out::println);
    }

    void delayExample() {
        Observable<String> stringObservable = Observable.just("a", "b", "c", "d", "e", "f");
        stringObservable.delay(4, TimeUnit.SECONDS)
                .subscribe(System.out::println);
        sleep();
    }

    void repeatExample() {
        Observable.just("1", 2, "3", 8.0001, 5.0, 6454654).repeat(2).subscribe(System.out::println);
    }

    void scanExample() {
        //Observable.just(1,2,3,4,5,6).scan(Integer::sum).subscribe(System.out::println);
        Observable.just("a", "b", "c", "dddddd", "eee")
                .map(String::length)
                .scan(0, Integer::sum)
                .subscribe(System.out::println);
    }

    //Reducing Operators

    void countExample() {
        Observable.just("a", 4, 4.0, 3333333, 45.00000000001, "def").count().subscribe(System.out::println);
    }

    void reduceExample() {
        //Observable.just(12,3,4,5,6,7,8).reduce((a,b)->a+b).subscribe(System.out::println);
        Observable.just(12, 3, 4, 5, 6, 7, 8).reduce("", (a, b) -> a + (a.equals("") ? "" : ",") + b).subscribe(System.out::println);
    }

    void allExample() {
        Observable.just(1, 2, 3, 4, 5, 6, 7, 8).all(i -> i <= 10).subscribe(System.out::println);
    }

    void anyExample() {
        Observable.just(1, 2, 3, 4, 5, 12, 7, 8).any(i -> i > 10).subscribe(System.out::println);
    }

    void containsExample() {
        Observable.range(1, 5000).contains(5999).subscribe(System.out::println);
    }

    //Collection Operators

    void listExample() {
        //Observable.just("a","b","c","d","e").toList().subscribe(System.out::println);
        //Observable.just("a","b","c","d","e").toList(6).subscribe(System.out::println);
        Observable.just("a", "b", "c", "d", "e").toList(CopyOnWriteArrayList::new).subscribe(System.out::println);
    }

    void sortedList() {
        //Observable.just(1,6,8,3,5,6).toSortedList().subscribe(System.out::println);
        Observable.just(1, 6, 8, 3, 5, 6).distinct().toSortedList().subscribe(System.out::println);
    }

    void toMapExample() {
        Observable<String> stringObservable = Observable.just("a", "bb", "c", "c", "c", "bb");
        stringObservable.sorted().toMap(s -> s, String::length).subscribe(System.out::println);
        stringObservable.sorted().toMultimap(s -> s, String::length).subscribe(System.out::println);
    }

    void collectExample() {
        Observable.just("Alpha", "Beta", "Beta", "Delta", "Epsilon")
                .collect(HashSet::new, HashSet::add)
                .subscribe(s -> System.out.println("Received: " + s));
    }

    //Error Recover Operators
    void onErrorReturnItemExample() {
        Observable<Integer> integerObservable = Observable.just(10, 20, 0, 90, 45, 2);
        integerObservable
                .map(i -> 10 / i)
                .onErrorReturnItem(-1)
                .subscribe(System.out::println,e->System.out.println("Error Received: "+e.getMessage()));
    }

    void onErrorReturnExample() {
        Single.just("2A")
                .map(v -> Integer.parseInt(v, 10))
                .onErrorReturn(error -> {
                    if (error instanceof NumberFormatException) return 0;
                    else throw new IllegalArgumentException();
                })
                .subscribe(
                        System.out::println,
                        error -> System.err.println("onError should not be printed!"));
    }

    void onErrorResumeNextExample(){
        Observable<Integer> numbers = Observable.generate(() -> 1, (state, emitter) -> {
            emitter.onNext(state);

            return state + 1;
        });
        numbers.scan((a,b)->Math.multiplyExact(a, b))
                .onErrorResumeNext(Observable.empty())
                .subscribe(
                        System.out::println,
                        error -> System.err.println("onError should not be printed!"));
    }

    void retryExample(){
        Observable.just(2,5,9,0,10,20)
                .map(i->10/i)
                .retry(1)
                .subscribe(System.out::println,e->System.out.println("Error Received: "+e.getMessage()));
    }

    //Action Operators
    void actionOperatorsExample(){
        Observable.just(1,2,3,0,5,6,7,8)
                .scan((a,b)->10/b)
                .doOnSubscribe(s->System.out.println("doOnSubscribe: "))
                .doOnNext(s->System.out.println("doOnNext: "+s))
                .doAfterNext(s->System.out.println("doAfterNext: "+s))
                .doOnComplete(()->System.out.println("doOnComplete: "))
                .doOnError(e->System.out.println("doOnError"))
                .doOnDispose(()->System.out.println("doOnDispose: "))
                .subscribe(System.out::println,e->System.out.println(e.getMessage()),()->System.out.println("Completed"));
    }
}

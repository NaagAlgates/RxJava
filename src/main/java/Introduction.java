/*
The Observable.create() factory allows us to create an Observable by providing a lambda receiving an Observable emitter.
We can call the Observable emitter's onNext() method to pass emissions (one a time) up the chain as well as onComplete()
to signal completion and communicate that there will be no more items.
*/

import io.reactivex.Observable;

class Introduction {
    public static void main(String args[]) {
        Examples examples = new Examples();
        //examples.firstExample();
        examples.secondExample();
    }


}

class Examples{
    void firstExample(){
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
        source.subscribe(data->System.out.println(data));
    }
    void secondExample(){
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
        source.filter(dataLength->dataLength.length()==3).subscribe(System.out::println);
    }
}
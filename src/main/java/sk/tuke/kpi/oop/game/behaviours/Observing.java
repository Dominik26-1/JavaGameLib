package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.messages.Topic;

import java.util.function.Predicate;

public class Observing<A extends Actor, T> implements Behaviour<A>{
    private Topic<T> topic;
    private Predicate<T> predicate;
    private Behaviour<A> delegate;
    public Observing(Topic<T> topic,Predicate<T> predicate, Behaviour<A> delegate) {
        this.delegate = delegate;
        this.predicate = predicate;
        this.topic = topic;

    }

    @Override
    public void setUp(A actor) {
        if(actor!=null){
            actor.getScene().getMessageBus().subscribe(this.topic,podmienka -> {
                if(predicate.test(podmienka)){
                    delegate.setUp(actor);
                }
            });
        }
    }
}

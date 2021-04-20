package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private float time;
    private boolean isActive;
    private Animation passiveBomb = new Animation("sprites/bomb.png");
    private Animation activeBomb = new Animation("sprites/bomb_activated.png", 16, 16,
        0.2f, Animation.PlayMode.LOOP_PINGPONG);
    private Animation explodedBomb = new Animation("sprites/small_explosion.png", 16, 16,
        0.2f, Animation.PlayMode.ONCE);
    private Disposable disposable;


    public TimeBomb(float time) {
        this.time = time;
        setAnimation(passiveBomb);
        this.isActive = false;
    }

    public void explode(){
        setAnimation(explodedBomb);
        this.disposable.dispose();
        this.disposable = new When<>(
            () -> this.getAnimation().getCurrentFrameIndex() >= this.getAnimation().getFrameCount()-1,
            new Invoke<>(() -> getScene().removeActor(this))
        ).scheduleFor(this);

    }


    public void activate() {
        this.isActive = true;
        setAnimation(activeBomb);
        this.disposable = new ActionSequence<>(
            new Wait<>(this.time),
            new Loop<>(new Invoke<>(this::explode))
        ).scheduleFor(this);
    }

    public boolean isActivated() {
        return this.isActive;
    }
}



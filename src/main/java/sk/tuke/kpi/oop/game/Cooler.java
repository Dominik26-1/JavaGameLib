package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor implements Switchable {
    private Animation animation = new Animation("sprites/fan.png", 32, 32, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
    private boolean isRunning = false;
    private Reactor reactor;


    public Cooler(Reactor reactor) {
        setAnimation(animation);
        this.isRunning = false;
        this.reactor = reactor;
        animation.pause();

    }
    private void coolReactor(){
        //decrease temperature of reactor
        if(this.reactor!=null && this.isRunning==true){
            this.reactor.decreaseTemperature(1);
        }
    }

    public void turnOn() {
        this.isRunning = true;
        this.animation.play();
    }

    public void turnOff() {
        this.isRunning = false;
        this.animation.pause();
    }



    public boolean isOn(){
        return this.isRunning;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        // use cooler
        if(this.reactor!=null){
            super.addedToScene(scene);
            new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
        }

    }
}

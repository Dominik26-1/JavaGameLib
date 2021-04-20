package sk.tuke.kpi.oop.game.fasade;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Switchable;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Laser extends AbstractActor implements Switchable, Sendable<Ripley> {
    private Animation animation = new Animation("sprites/laser.png",16,48,0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private Animation offAnimation = new Animation("sprites/laser.png",16,16);
    private boolean isOn;
    public Laser() {
        setAnimation(animation);
        this.isOn = true;
    }
    public void checkColision() {
        for (Actor actor : this.getScene().getActors()) {
            if (actor instanceof Alive && !(actor instanceof Enemy) && this.intersects(actor)
                && this.isOn ) {
                ((Alive) actor).getHealth().drain(((Alive)actor).getHealth().getValue());
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new ActionSequence<>(
            new Invoke<>(this::checkColision)
        )).scheduleFor(this);
    }


    @Override
    public void sendMessage(Ripley receiver) {
        String message = "Laser: Do not touch me. You can turn me off via button.";
        receiver.setMessage(message);
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        setAnimation(animation);
        this.getAnimation().play();

    }

    @Override
    public void turnOff() {
        if(isOn) {
            this.isOn = false;
            setAnimation(offAnimation);
            this.getAnimation().pause();
        }

    }


    @Override
    public boolean isOn() {
        return this.isOn;
    }
}


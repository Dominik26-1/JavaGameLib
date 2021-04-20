package sk.tuke.kpi.oop.game.fasade;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Electricity extends AbstractActor implements Sendable<Ripley> {

    private Animation animation = new Animation("sprites/electricity.png",16,48,0.3f, Animation.PlayMode.LOOP_PINGPONG);
    private boolean isOn;

    public Electricity() {
        setAnimation(animation);
        this.isOn = true;
    }

    public void checkColision() {
        for (Actor actor : this.getScene().getActors()) {
            if (actor instanceof Alive && !(actor instanceof Enemy) && this.intersects(actor)
                && this.isOn &&  this.getAnimation().getCurrentFrameIndex() >= this.getAnimation().getFrameCount()-1) {
                ((Alive) actor).getHealth().drain(30);
                this.animation.stop();
            }
        }
        this.animation.play();
    }


    @Override
    public void sendMessage(Ripley receiver) {
        String message = "Electric:You have to go thought only when I am recharging.\nCommands: ENTER - TAKE, BackSpace - DROP, U - OPEN, B - Apply, S - SHIFT, I - INFO";
        receiver.setMessage(message);

    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new ActionSequence<>(
            new Invoke<>(this::checkColision)
        )).scheduleFor(this);
    }
}

package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Bullet extends AbstractActor implements Fireable {
    private Animation animation = new Animation("sprites/bullet.png",16,16);
    public Bullet() {
        setAnimation(animation);
    }
    public void checkColision(){
        for (Actor actor: this.getScene().getActors()){
            if(actor instanceof Alive && this.intersects(actor) && !(actor instanceof Ripley)){
               ((Alive) actor).getHealth().drain(10);
                this.stoppedMoving();
                this.getScene().cancelActions(this);
                this.getScene().removeActor(this);


            }
        }
    }

    @Override
    public int getSpeed() {
        return 4;
    }

    @Override
    public void startedMoving(Direction direction) {
        this.animation.setRotation(direction.getAngle());

    }

    @Override
    public void stoppedMoving() {

    }

    @Override
    public void collidedWithWall() {
        this.getScene().cancelActions(this);
        this.getScene().removeActor(this);
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::checkColision)).scheduleFor(this);
    }
}

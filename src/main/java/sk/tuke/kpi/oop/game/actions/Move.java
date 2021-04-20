package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;


public class Move<A extends Movable> implements Action<Movable> {

    private Direction direction;
    private float duration;
    private boolean maxDuration;
    private Movable actor;
    private int count = 0;
    private float originalDuration;

    public Move(Direction direction, float duration) {
        // implementacia konstruktora akcie
        this.direction = direction;
        this.duration = duration;
        this.maxDuration = true;
        this.originalDuration = duration;
    }

    public Move(Direction direction) {
        this.direction = direction;
        this.maxDuration = true;
        this.duration = Integer.MAX_VALUE;
        this.originalDuration = Integer.MAX_VALUE;
    }

    @Override
    public @Nullable Movable getActor() {
        return this.actor;
    }

    @Override
    public void setActor(@Nullable Movable actor) {
        this.actor = actor;
    }

    @Override
    public void reset() {
        this.count = 0;
        this.duration = this.originalDuration;

    }

    @Override
    public boolean isDone() {
        if (this.count > 0 && this.duration <= 1e-5) {
            return true;
        }
        return false;

    }

    @Override
    public void execute(float deltaTime) {

        if (this.actor == null || this.isDone()) {
            return;
        }
        if (this.direction == Direction.NONE || (count > 0 && !this.maxDuration) || this.isDone()) {
            this.stop();
            return;

        }

        if (this.count == 0) {
            this.actor.startedMoving(this.direction);
            this.count++;
        }

        //if (!this.maxDuration) {
        this.duration -= deltaTime;
         //}
        this.calculatePosition();
        //System.out.println(this.duration);
    }

    public void stop() {
        this.duration = 0;
        this.count = 1;
        if (this.actor != null) {
            this.actor.stoppedMoving();
            return;
        }
    }


    public void calculatePosition() {
        int newPosX = actor.getPosX() + direction.getDx() * actor.getSpeed();
        int newPosY = actor.getPosY() + direction.getDy() * actor.getSpeed();
        if (!actor.getScene().getMap().intersectsWithWall(actor)) {
            newPosX = actor.getPosX();
            newPosY = actor.getPosY();
            actor.setPosition(actor.getPosX() + this.direction.getDx() * actor.getSpeed(), actor.getPosY() + this.direction.getDy() * actor.getSpeed());

            if (actor.getScene().getMap().intersectsWithWall(actor)) {
                actor.setPosition(newPosX, newPosY);
                this.stop();
                actor.collidedWithWall();


            }
        }
        if(this.duration <= 1e-5){
            this.stop();
        }
    }
}

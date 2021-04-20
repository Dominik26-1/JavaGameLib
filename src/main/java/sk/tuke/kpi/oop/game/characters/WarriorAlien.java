package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class WarriorAlien extends Children implements Movable, Enemy, Alive {
    private Animation animation = new Animation("sprites/alien.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private Health health;
    private Behaviour<WarriorAlien> behaviour;
    public WarriorAlien(int Health,Behaviour<WarriorAlien> behaviour, String name) {
        super();
        setAnimation(animation);
        this.health = new Health(Health);
        this.behaviour = behaviour;
    }

    @Override
    public Children createClone() {
        return new WarriorAlien(50,null,this.getName());
    }


    @Override
    public int getSpeed() {
        return 1;
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    public void checkColision() {
        for (Actor actor : this.getScene().getActors()) {
            if (actor instanceof Alive && !(actor instanceof Enemy) && this.intersects(actor)
                && ((Alive) actor).isVisible()) {
                ((Alive) actor).getHealth().drain(1);
            }
        }
    }
    @Override
    public void collidedWithWall() {
        this.behaviour = null;
        this.behaviour.setUp(this);


    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (this.behaviour != null) {
            this.behaviour.setUp(this);
        }
        new Loop<>(new ActionSequence<>(
            new Wait<>((float) 0.05),
            new Invoke<>(this::checkColision)
        )).scheduleFor(this);
        this.health.onExhaustion(() -> {
            this.getScene().removeActor(this);
        });
    }
}

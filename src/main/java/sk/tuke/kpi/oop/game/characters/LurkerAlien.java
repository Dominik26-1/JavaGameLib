package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;
import sk.tuke.kpi.oop.game.behaviours.ChaisingMoving;

public class LurkerAlien extends Alien {
    private Animation animation = new Animation("sprites/lurker_alien.png",32,32,0.2f, Animation.PlayMode.LOOP_PINGPONG);
    private Behaviour<? super Alien> behaviour;
    public LurkerAlien(int Health) {
        super(Health, null);
        this.behaviour = behaviour;
        setAnimation(animation);
    }


    @Override
    public void collidedWithWall() {
        this.behaviour = null;
        this.behaviour = new ChaisingMoving();
        this.behaviour.setUp(this);
    }

    public void startMove(){
        new Loop<>(
                new ActionSequence<>(
                    new Wait<>(1.5f),
                    new Invoke<>(()->this.collidedWithWall())
                )).scheduleFor(this);

    }


    @Override
    public int getSpeed() {
        return 1;
    }

}

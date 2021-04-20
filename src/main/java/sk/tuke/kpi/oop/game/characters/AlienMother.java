package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

import java.util.HashMap;
import java.util.Map;

public class AlienMother extends Alien{
    private Animation animation = new Animation("sprites/mother.png",112,162,0.2f, Animation.PlayMode.LOOP_PINGPONG);
    private Health health;
    private Behaviour<? super Alien> behaviour;
    private Map<Class<?>, Children> childrenMap;
    public AlienMother() {
        setAnimation(animation);
        this.health = new Health(200);
        this.behaviour = null;
    }
    public AlienMother(int health, Behaviour<? super Alien> behaviour) {
        this.childrenMap = new HashMap<>();
        this.childrenMap.put(Alien.class, new Alien());
        setAnimation(animation);
        this.health = new Health(health);
        this.behaviour = behaviour;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(()-> this.getScene().addActor(this.childrenMap.get(Alien.class).createClone(),this.getPosX()+this.getWidth()/2
                ,this.getPosY()+this.getHeight()/2))
        )).scheduleFor(this);

    }

}

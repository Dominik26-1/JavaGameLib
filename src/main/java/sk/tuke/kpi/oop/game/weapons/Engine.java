package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.Fire;
import sk.tuke.kpi.oop.game.characters.Armed;

public class Engine extends AbstractActor implements Armed {
    private Animation animation = new Animation("sprites/engine.png",96,64,1.0f, Animation.PlayMode.LOOP_PINGPONG);
    private Firearm rocketGun;

    public Engine(String name) {
        super(name);
        setAnimation(animation);
        this.rocketGun = new RocketGun(Integer.MAX_VALUE);


    }

    @Override
    public Firearm getFirearm() {
        return this.rocketGun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.rocketGun = weapon;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(
            new ActionSequence<>(
            new Wait<>(1),
            new Invoke<>(()->new Fire<>().scheduleFor(this)))).scheduleFor(this);

    }
}

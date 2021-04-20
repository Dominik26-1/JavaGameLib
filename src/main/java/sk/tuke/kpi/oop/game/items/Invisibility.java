package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Informative;
import sk.tuke.kpi.oop.game.characters.Ripley;


public class Invisibility extends AbstractActor implements Usable<Ripley>, Informative<Ripley> {
    private float timer;
    private Animation animation = new Animation("sprites/box_large.png");
    public Invisibility() {
        this.timer = 5.0f;
        setAnimation(animation);
    }

    @Override
    public void useWith(Ripley actor) {
        if(actor==null){
            return;
        }
        actor.setInvisible();
        actor.getScene().removeActor(this);
        new ActionSequence<>(
            new Wait<>(this.timer),
            new Invoke<>(actor1 -> {actor.setVisible();})
            ).scheduleFor(actor);


    }

    @Override
    public void showInfofor(Ripley actor) {
        actor.setMessage("You become a invisible for alien for 5 sec.");
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}

package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Informative;
import sk.tuke.kpi.oop.game.characters.Ripley;


public class Info<A extends Ripley> extends AbstractAction<Ripley> {
    private float duration;
    private Ripley actor;
    private Informative<A> sender;

    public Info(Informative<A> actor,float duration) {
        this.sender = actor;
        this.duration = duration;
    }

    @Override
    public void setActor(@Nullable Ripley actor) {
        this.actor = actor;
    }

    @Override
    public @Nullable Ripley getActor() {
        return this.actor;
    }

    @Override
    public void execute(float deltaTime) {
        this.sender.showInfofor(this.actor);
        this.duration -= deltaTime;

        //}
        if (this.duration <= 1e-5) {
            this.actor.setMessage(null);
            setDone(true);
            return;
        }
    }
}

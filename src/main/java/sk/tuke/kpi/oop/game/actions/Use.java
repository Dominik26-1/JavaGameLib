package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.items.Usable;

public class Use<X extends Actor> extends AbstractAction<X> {
    private Usable<X> usable;

    public Use(Usable<X> usable) {
        this.usable = usable;
    }

    @Override
    public void execute(float deltaTime) {
        if (this.getActor() != null) {
            usable.useWith(this.getActor());

        }
        setDone(true);

    }
    public Disposable scheduleForIntersectingWith(Actor mediatingActor) {
        Scene scene = mediatingActor.getScene();
        if (scene == null) return null;
        Class<X> usingActorClass = usable.getUsingActorClass();  // `usable` je spominana clenska premenna

        for (Actor actor : scene) {
            if (mediatingActor.intersects(actor) && usingActorClass.isInstance(actor)) {
                return this.scheduleFor(usingActorClass.cast(actor));  // naplanovanie akcie v pripade, ze sa nasiel vhodny akter
            }
        }
        return null;
    }

}

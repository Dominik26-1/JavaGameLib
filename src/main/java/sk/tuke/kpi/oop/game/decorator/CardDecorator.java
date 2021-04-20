package sk.tuke.kpi.oop.game.decorator;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public abstract class CardDecorator extends AbstractActor implements Usable<LockedDoor>, Collectible {
    protected Usable<LockedDoor> decoratedAccessCard;

    public CardDecorator(Usable<LockedDoor> decoratedAccessCard) {
        this.decoratedAccessCard = decoratedAccessCard;
    }


    @Override
    public void useWith(LockedDoor actor) {
        this.decoratedAccessCard.useWith(actor);
    }
}

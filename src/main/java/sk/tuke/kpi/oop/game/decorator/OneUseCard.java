package sk.tuke.kpi.oop.game.decorator;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class  OneUseCard extends CardDecorator{
    private Animation animation = new Animation("sprites/key.png");
    public OneUseCard(Usable<LockedDoor> decoratedAccessCard) {
        super(decoratedAccessCard);
        setAnimation(animation);
    }

    @Override
    public void useWith(LockedDoor actor) {
        super.useWith(actor);
        actor.getScene().getFirstActorByType(Keeper.class).getBackpack().remove(this);

    }

    @Override
    public Class<LockedDoor> getUsingActorClass() {
        return LockedDoor.class;
    }
}

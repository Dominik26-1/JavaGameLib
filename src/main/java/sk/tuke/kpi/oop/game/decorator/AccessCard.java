package sk.tuke.kpi.oop.game.decorator;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;
import sk.tuke.kpi.oop.game.openables.LockedDoor;

public class AccessCard extends AbstractActor implements Collectible, Usable<LockedDoor> {
    private Animation animation = new Animation("sprites/key.png",16,16);
    public AccessCard() {
        setAnimation(animation);
    }


    @Override
    public void useWith(LockedDoor actor) {
        if(actor.isLocked()){
            actor.unlock();
        }
        else{
            actor.lock();
        }

    }

    @Override
    public Class<LockedDoor> getUsingActorClass() {
        return LockedDoor.class;
    }
}

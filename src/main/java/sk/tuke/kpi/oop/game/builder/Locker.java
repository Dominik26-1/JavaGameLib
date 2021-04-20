package sk.tuke.kpi.oop.game.builder;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable<Ripley> {
    private Animation animation = new Animation("sprites/locker.png",16,16);
    private int remainingUses;
    private Collectible collectible;
    public Locker(String name) {
        super(name);
        setAnimation(animation);

    }
    public int getRemainingUses(){
        return this.remainingUses;
    }

    public void setRemainingUses(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    public void setCollectible(Collectible collectible) {
        this.collectible = collectible;
    }

    @Override
    public void useWith(Ripley actor) {
        if(this.remainingUses>0 && actor!=null){
            this.remainingUses -=1;
            if(this.collectible!=null){
                actor.getBackpack().add(this.collectible);
            }

        }
    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
}

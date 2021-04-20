package sk.tuke.kpi.oop.game.builder;

import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.decorator.AccessCard;

public class CardLockerBuilder extends Locker implements Builder {
    private Locker locker;
    public CardLockerBuilder(String name) {
        super(name);
        this.locker = new Locker(name);
    }

    @Override
    public void createItem() {
        this.locker.setCollectible(new AccessCard());

    }

    @Override
    public void setUses() {
        this.locker.setRemainingUses(1);

    }

    @Override
    public int getUses() {
        return this.locker.getRemainingUses();
    }

    @Override
    public void useWith(Ripley actor) {
        this.locker.useWith(actor);
    }
}

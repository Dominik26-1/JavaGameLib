package sk.tuke.kpi.oop.game.builder;

import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Hammer;

public class HammerLockerBuilder extends Locker implements Builder {
    private Locker locker;
    public HammerLockerBuilder(String name) {
        super(name);
        this.locker = new Locker(name);
    }

    @Override
    public void createItem() {
        this.locker.setCollectible(new Hammer());

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

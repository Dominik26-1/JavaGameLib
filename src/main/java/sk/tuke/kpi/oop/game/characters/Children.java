package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class Children extends AbstractActor{
    public Children() {
        super();
    }
    public abstract Children createClone();
}

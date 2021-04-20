package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.oop.game.characters.Ripley;

public interface Informative<A extends Ripley> {
    void showInfofor(Ripley actor);
}

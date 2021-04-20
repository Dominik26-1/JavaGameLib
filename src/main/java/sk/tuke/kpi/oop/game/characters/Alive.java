package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Actor;



public interface Alive extends Actor {
    Health getHealth();
    default boolean isVisible(){return true;}
}

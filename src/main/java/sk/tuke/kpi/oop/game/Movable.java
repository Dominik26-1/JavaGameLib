package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;

public interface Movable extends Actor {
    int getSpeed();
    default void startedMoving(Direction direction) {};// ako listener pre udalosť zacatia pohybu v smere direction.
    default void stoppedMoving() {};// ako listener pre udalosť zastavenia pohybu.
    default void collidedWithWall() {};
}

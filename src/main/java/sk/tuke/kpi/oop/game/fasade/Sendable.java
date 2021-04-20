package sk.tuke.kpi.oop.game.fasade;

import sk.tuke.kpi.gamelib.Actor;

public interface Sendable<T extends Actor> {
    void sendMessage(T receiver);
}

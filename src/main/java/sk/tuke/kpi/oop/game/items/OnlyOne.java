package sk.tuke.kpi.oop.game.items;


import sk.tuke.kpi.gamelib.Actor;

public interface OnlyOne<T extends Actor> {
    Class<T> getActorClass();
}

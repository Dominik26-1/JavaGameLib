package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.*;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;


public class KeeperController implements KeyboardListener {

    private Keeper keeper;


    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }

    public void useAction() {
        for (Actor actor : this.keeper.getScene().getActors()) {
            if (actor.intersects(this.keeper) && actor instanceof Usable) {
                new Use<>((Usable<?>) actor).scheduleForIntersectingWith(keeper);
            }
        }

    }


    public void applyAction() {
        Collectible firstItem = this.keeper.getBackpack().peek();
        if (firstItem instanceof Usable) {
            new Use<>((Usable<?>) firstItem).scheduleForIntersectingWith(keeper);
        }
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        switch (key) {
            case ENTER:
                new Take<>().scheduleFor(keeper);
                break;
            case S:
                new Shift<>().scheduleFor(keeper);
                break;
            case U:
                this.useAction();
                break;
            case BACKSPACE:
                new Drop<>().scheduleFor(keeper);
                break;
            case B:
                this.applyAction();
                break;
            default:
                break;


        }
    }
}

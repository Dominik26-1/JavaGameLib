package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Informative;
import sk.tuke.kpi.oop.game.actions.Info;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class InfoController implements KeyboardListener {
    private Ripley infoReceiver;
    public InfoController(Ripley ripley) {
        infoReceiver = ripley;
    }
    public void infoAction() {
        for (Actor actor : this.infoReceiver.getScene().getActors()) {
            if (actor.intersects(this.infoReceiver) && actor instanceof Informative) {
                new Info<>((Informative<?>) actor, 2.0f).scheduleFor(this.infoReceiver);
            }
        }
    }


    @Override
    public void keyPressed(Input.@NotNull Key key) {
        if (key== Input.Key.I){
            this.infoAction();
        }
    }


}

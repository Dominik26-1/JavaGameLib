package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Wrench;

public class FirstSteps implements SceneListener {

    private Ripley ripley = new Ripley();


    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        scene.addActor(ripley, 0,0);

        //new ActionSequence<>(move,left,up,right).scheduleFor(ripley);
        MovableController movement = new MovableController(ripley);
        KeeperController keeper = new KeeperController(ripley);
        Energy energy = new Energy();
        scene.addActor(energy,100,100);

        new Use<>(energy).scheduleFor(ripley);

        Wrench wrench = new Wrench();
        FireExtinguisher fireExtinguisher = new FireExtinguisher();
        Hammer hammer = new Hammer();

        ripley.getBackpack().add(fireExtinguisher);
        ripley.getBackpack().add(wrench);
        ripley.getBackpack().add(hammer);

        scene.getGame().pushActorContainer(ripley.getBackpack());

        scene.getInput().registerListener(movement);
        scene.getInput().registerListener(keeper);



    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        ripley.showRipleyState();
    }
}

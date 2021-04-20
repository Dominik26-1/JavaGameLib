
package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.builder.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.decorator.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;

public class MissionImpossible implements SceneListener {

    private Ripley ripley;

    public static class Factory implements ActorFactory {

        @Override
        public Actor create(@Nullable String type, @Nullable String name) {
            switch (name) {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "ventilator":
                    return new Ventilator();
                case "locker":
                    return new Locker(name);
                case "access card":
                    return new AccessCard();
                /*case "door":
                    return new LockedDoor();*/
                default:
                    return null;
            }
        }

    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ripley = scene.getFirstActorByType(Ripley.class);


        //new ActionSequence<>(move,left,up,right).scheduleFor(ripley);
        MovableController movement = new MovableController(ripley);
        KeeperController keeper = new KeeperController(ripley);
        Disposable move = scene.getInput().registerListener(movement);
        Disposable keep = scene.getInput().registerListener(keeper);


        //new Use<>(energy).scheduleFor(ripley);



        //scene.getMessageBus().subscribe(Door.DOOR_OPENED,door1 -> ripley.decreaseEnergy());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED,ripley1 -> move.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED,ripley1 -> keep.dispose());
        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED,ventilator1 -> ripley.stopDecreasing());

        scene.getGame().pushActorContainer(ripley.getBackpack());


    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        this.ripley.showRipleyState();
        scene.follow(this.ripley);



    }
}

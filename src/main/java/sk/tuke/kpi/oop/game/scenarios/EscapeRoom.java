package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

public class EscapeRoom implements SceneListener {
    private Ripley ripley;

    public static class Factory implements ActorFactory {

        @Override
        public Actor create(@Nullable String type, @Nullable String name) {
            switch (name) {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "ammo":
                    return new Ammo();
                case "alien":
                    switch (type) {
                        case "running":
                            return new Alien(50,new RandomlyMoving());
                        case "waiting1":
                            return this.createWaitingAlien();
                        case "waiting2":
                            return this.createWaitingAlien();
                        default:
                            return new Alien();
                    }
                case "alien mother":
                    return new AlienMother(50,null);
                case "front door":
                    return new Door("front door", Door.Orientation.VERTICAL);
                case "back door":
                    return new Door("back door", Door.Orientation.HORIZONTAL);
                case "exit door":
                    if(type.equals("vertical")){
                        return new Door("exit door", Door.Orientation.VERTICAL);
                    }
                    if(type.equals("horizontal")){
                        return new Door("exit door", Door.Orientation.HORIZONTAL);
                    }
                    return null;
                default:
                    return null;
            }
        }


        private Alien createWaitingAlien() {
            return new Alien(50,
                new Observing<>(
                    Door.DOOR_OPENED,  // cakame na spravu v tejto teme
                    door -> door.getName().equals("front door")  || door.getName().equals("back door"),
                    new RandomlyMoving()  // odovzdavame spravania, ktore sa ma pouzit v pripade, ze predikat bude splneny
                )
            );
        }
    }
    private String messageText;

    @Override
    public void sceneCreated(@NotNull Scene scene) {
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ripley = scene.getFirstActorByType(Ripley.class);


        //new ActionSequence<>(move,left,up,right).scheduleFor(ripley);
        MovableController movement = new MovableController(ripley);
        KeeperController keeper = new KeeperController(ripley);
        ShooterController shooting = new ShooterController(ripley);
        Disposable move = scene.getInput().registerListener(movement);
        Disposable keep = scene.getInput().registerListener(keeper);
        Disposable shoot = scene.getInput().registerListener(shooting);
        Energy energy = (Energy) scene.getFirstActorByName("energy");
        energy.useWith(ripley);



        //new Use<>(energy).scheduleFor(ripley);



        scene.getMessageBus().subscribe(Door.DOOR_OPENED, door1 -> {
            if(door1.equals("exit door")){
                this.messageText = "Well Done";
            }
            });
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED,ripley1 -> move.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED,ripley1 -> keep.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED,ripley1 -> shoot.dispose());
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED,ripley1 -> shoot.dispose());
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, door -> {
            if (door.getName().equals("exit door")) {
                scene.getOverlay().drawText("Well done", 0, 0);
            }
        });


        scene.getGame().pushActorContainer(ripley.getBackpack());


    }


    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        this.ripley.showRipleyState();
        scene.follow(this.ripley);
        if(this.messageText!=null){
            scene.getOverlay().drawText(messageText, 0, 0);
        }





    }

}

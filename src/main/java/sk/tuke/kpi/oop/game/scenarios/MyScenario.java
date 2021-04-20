package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.builder.*;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.LurkerAlien;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.InfoController;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.decorator.AccessCard;
import sk.tuke.kpi.oop.game.decorator.OneUseCard;
import sk.tuke.kpi.oop.game.fasade.Electricity;
import sk.tuke.kpi.oop.game.fasade.Laser;
import sk.tuke.kpi.oop.game.fasade.Messenger;
import sk.tuke.kpi.oop.game.items.*;
import sk.tuke.kpi.oop.game.openables.CodeDoor;
import sk.tuke.kpi.oop.game.openables.Door;
import sk.tuke.kpi.oop.game.openables.LockedDoor;
import sk.tuke.kpi.oop.game.weapons.Engine;


public class MyScenario implements SceneListener {
    private Ripley ripley;

    public static class Factory implements ActorFactory {

        @Override
        public Actor create(@Nullable String type, @Nullable String name) {
            if (name.contains("teleport")) {
                return new Teleport(name, null);
            }
            if(name.contains("ndoor")){
                switch(type){
                    case "vertical":
                        return new Door(name, Door.Orientation.VERTICAL);
                    default:
                        return new Door(name, Door.Orientation.HORIZONTAL);
                }

            }
            if(name.contains("locker")){
                switch(type){
                    case "access":
                        return new CardLockerBuilder(name);
                    default:
                        return new HammerLockerBuilder(name);
                }

            }
            switch (name) {
                case "ellen":
                    return new Ripley();
                case "energy":
                    return new Energy();
                case "laser":
                    return new Laser();
                case "engine":
                    return new Engine(name);
                case "stone":
                    return new Stone();
                case "ammo":
                    return new Ammo();
                case "ventilator":
                    return new Ventilator();
                case "decorated card":
                    return new OneUseCard(new AccessCard());
                case "GreenButton":
                    return new Button(null);
                case "electricity":
                    return new Electricity();
                case "alien":
                    switch (type) {
                        case "running":
                            return new Alien(50, new RandomlyMoving());
                        case "waiting1":
                            return this.createWaitingAlien();
                        default:
                            return new Alien();
                    }
                case "code key":
                    return new CodeCard(type);
                case "computer":
                    return MessageComputer.getInstance();
                case "invisible":
                    return new Invisibility();
                case "alien mother":
                    return new AlienMother(50, null);
                case "locked door":
                    return new LockedDoor("locked door", Door.Orientation.HORIZONTAL);
                case "messenger":
                    return new Messenger();
                case "lurker alien":
                    return new LurkerAlien(150);
                case "exit door":
                    switch (type) {
                        case "vertical":
                            return new CodeDoor(name, Door.Orientation.VERTICAL, "075463");
                        case "horizontal":
                            return new CodeDoor(name, Door.Orientation.HORIZONTAL, "075463");
                        default:
                            return null;
                    }
                default:
                    return null;
            }

        }


        private Alien createWaitingAlien() {
            return new Alien(50,
                new Observing<>(
                    Door.DOOR_OPENED,  // cakame na spravu v tejto teme
                    door -> door.getName().equals("danger ndoor"),
                    new RandomlyMoving()  // odovzdavame spravania, ktore sa ma pouzit v pripade, ze predikat bude splneny
                )
            );
        }

    }

    private String messageText;


    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        ripley = (Ripley) scene.getFirstActorByName("Ellen");


        //new ActionSequence<>(move,left,up,right).scheduleFor(ripley);
        MovableController movement = new MovableController(ripley);
        KeeperController keeper = new KeeperController(ripley);
        ShooterController shooting = new ShooterController(ripley);
        InfoController informating = new InfoController(ripley);
        Disposable move = scene.getInput().registerListener(movement);
        Disposable keep = scene.getInput().registerListener(keeper);
        Disposable shoot = scene.getInput().registerListener(shooting);
        Disposable info = scene.getInput().registerListener(informating);
        Teleport t1 = (Teleport) scene.getFirstActorByName("teleport1");
        Teleport t2 = (Teleport) scene.getFirstActorByName("teleport2");
        Button button = scene.getFirstActorByType(Button.class);
        button.setDevice(scene.getFirstActorByType(Laser.class));
        Messenger messenger = scene.getFirstActorByType(Messenger.class);
        messenger.setReceiver(ripley);
        messenger.setSenders(scene.getFirstActorByType(Laser.class),scene.getFirstActorByType(Electricity.class));
        Company MakeLocker = new LockerCompany();
        MakeLocker.build((Builder) scene.getFirstActorByName("locker1"));
        MakeLocker.build((Builder) scene.getFirstActorByName("locker2"));
        MakeLocker.build((Builder) scene.getFirstActorByName("locker3"));


        t1.setDestination(t2);
        t2.setDestination(t1);




        scene.getMessageBus().subscribe(Door.DOOR_OPENED, door1 -> {
            if (door1.getName().equals("exit door")) {
                this.messageText = "Well Done";
                move.dispose();
                keep.dispose();
                shoot.dispose();
                info.dispose();
                new ActionSequence<>(
                    new Wait<>(2),
                    new Invoke<>(()->scene.getGame().stop())
                ).scheduleFor(ripley);

            }
        });
        scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, ripley1 -> {move.dispose();
            keep.dispose();
            shoot.dispose();
            info.dispose();
        this.messageText = "You lose";});
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, door1 -> {
            if (door1.getName().equals("front ndoor")) {
                new ActionSequence<>(
                    new Invoke<>(()->messenger.laserSending()),
                        new Wait<>(3),
                       new Invoke<>(()->ripley.setMessage(null))
                ).scheduleFor(ripley);
            }
        });
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, door1 -> {
            if (door1.getName().equals("back ndoor")) {
                scene.getFirstActorByType(LurkerAlien.class).startMove();
            }
        });
        scene.getMessageBus().subscribe(Door.DOOR_OPENED, door1 -> {
            if (door1.getName().equals("back ndoor")) {
                ripley.decreaseEnergy();
        }});
        scene.getMessageBus().subscribe(CodeCard.INCORRECT_CODE, door1 -> {
            ripley.setMessage("Incorrect code to open final door");
        });


        scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED,ripley1 -> {ripley.stopDecreasing();});

        scene.getGame().pushActorContainer(ripley.getBackpack());
        new ActionSequence<>(
            new Invoke<>(()->messenger.electricitySending())
        ).scheduleFor(ripley);

    }


    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        this.ripley.showRipleyState(this.messageText);
        scene.follow(this.ripley);

    }

}


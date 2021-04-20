package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.gamelib.map.MapMarker;
import sk.tuke.kpi.oop.game.*;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Wrench;

import java.util.Map;

public class TrainingGameplay extends Scenario {
    @Override
    public void setupPlay(@NotNull Scene scene) {
        Map<String, MapMarker> markers = scene.getMap().getMarkers();


        // ziskanie referencie na marker nazvany "reactor-area-1"
        MapMarker reactorArea1 = markers.get("reactor-area-1");
        MapMarker coolerArea1 = markers.get("cooler-area-1");
        MapMarker computerArea = markers.get("computer-area");
        //create,set position and turn on reacter
        Reactor reactor = new Reactor();
        scene.addActor(reactor, reactorArea1.getPosX(), reactorArea1.getPosY());
        reactor.turnOn();

        //set correct switch to remote reactor
        PowerSwitch switcher_reactor = new PowerSwitch(reactor);
        scene.addActor(switcher_reactor,50,64);

        //create cooler and its switch
        Cooler cooler = new Cooler(reactor);
        scene.addActor(cooler, coolerArea1.getPosX(),coolerArea1.getPosY());
        PowerSwitch switcher_cooler = new PowerSwitch(cooler);
        scene.addActor(switcher_cooler,130,150);

        //create light and its connecting to reactor
        Light light =  new Light();
        scene.addActor(light, 300,100);
        reactor.addDevice(light);
        PowerSwitch switcher_light = new PowerSwitch(light);
        scene.addActor(switcher_light, 280, 100);

        //create computer and its connecting to reactor
        Computer pc = new Computer();
        scene.addActor(pc,computerArea.getPosX(),computerArea.getPosY());
        //PowerSwitch switcher_pc = new PowerSwitch();
        reactor.addDevice(pc);

        //automatically turn on cooler after 5s
        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(cooler::turnOn)
        ).scheduleFor(cooler);

        //create hammer
        Hammer hammer = new Hammer();
        scene.addActor(hammer, 190,180);

        Wrench wrench = new Wrench();
        scene.addActor(wrench, 100,100);


        DefectiveLight damaged_light = new DefectiveLight();
        scene.addActor(damaged_light,200,100);
        reactor.addDevice(damaged_light);

        TimeBomb bomb = new TimeBomb(10);
        scene.addActor(bomb, 250,100);
        bomb.activate();

        ChainBomb bomb1 = new ChainBomb(10);
        scene.addActor(bomb1,250,70);

        ChainBomb bomb2 = new ChainBomb(5);
        scene.addActor(bomb2,220,70);
        bomb2.activate();

        ChainBomb bomb3 = new ChainBomb(10);
        scene.addActor(bomb3,190,70);

        ChainBomb bomb4 = new ChainBomb(10);
        scene.addActor(bomb4,160,70);


        Teleport A = new Teleport(null);
        scene.addActor(A,64,122);

        Teleport B = new Teleport(null);
        scene.addActor(B,50,300);

        Teleport C = new Teleport(null);
        scene.addActor(C,250,300);

        A.setDestination(B);
        B.setDestination(C);



        //automatically repair reactor according to its temperature
        new When<>(
            () -> reactor.getTemperature() >= 3000,
            new Invoke<>(() -> hammer.useWith(reactor))
        ).scheduleFor(reactor);


    }
}

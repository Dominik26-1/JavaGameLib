package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.gamelib.backends.lwjgl.LwjglBackend;
import sk.tuke.kpi.oop.game.scenarios.MyScenario;

public class Main {
    public static void main(String[] args) {
        // nastavenie okna hry: nazov okna a jeho rozmery
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);

        // vytvorenie instancie hernej aplikacie
        // pouzijeme implementaciu rozhrania `Game` triedou `GameApplication`
        Game game = new GameApplication(windowSetup, new LwjglBackend());

        // vytvorenie sceny pre hru
        // pouzijeme implementaciu rozhrania `Scene` triedou `World`
       // Scene scene = new World("world","maps/mission-impossible.tmx");
        //Scene scene = new World("mission-impossible", "maps/mission-impossible.tmx", new MissionImpossible.Factory());
       // Scene scene = new World("escape-room","maps/escape-room.tmx",new EscapeRoom.Factory());
        Scene scene = new World("my-room","maps/my-room.tmx",new MyScenario.Factory());
        // pridanie sceny do hry
        game.addScene(scene);


        //MissionImpossible scenar2 = new MissionImpossible();
        MyScenario scenar2 = new MyScenario();
        //EscapeRoom scenar2 = new EscapeRoom();
        //FirstSteps scenar = new FirstSteps();
        //scene.addListener(scenar);
        scene.addListener(scenar2);
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);

        // spustenie hry
        game.start();


    }
}

package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;

import java.util.ArrayList;
import java.util.List;

public class MessageComputer extends Computer implements Usable<Ripley> {
    private List<String> instructions;
    private int currentMessage = 0;

    private static MessageComputer instance= new MessageComputer();
    private MessageComputer() {
        super();
        this.instructions = new ArrayList<>();
    }
    private void initialize(){
        this.instructions.add("Here are some instructions");
        this.instructions.add("You have to open exit final door at down on the map");
        this.instructions.add("To open the door you have to have the key");
        this.instructions.add("Attention! Only one key have a correct code to open door");
        this.instructions.add("To get the correct key, you have to find the way to get it");
        this.instructions.add("On your way you have som enemy, so take care");
        this.instructions.add("The best of luck our hero!");
        this.instructions.add(null);

    }
    public static MessageComputer getInstance(){
        return instance;
    }

    @Override
    public void useWith(Ripley actor) {
        if(actor==null){
            return;
        }
        if(this.currentMessage>=instructions.size()){
            this.currentMessage = 0;
        }
        actor.setMessage(this.instructions.get(this.currentMessage));
        this.currentMessage++;

    }

    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.initialize();

    }
}

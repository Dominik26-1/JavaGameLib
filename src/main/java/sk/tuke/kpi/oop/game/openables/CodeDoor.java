package sk.tuke.kpi.oop.game.openables;


import sk.tuke.kpi.oop.game.Informative;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class CodeDoor extends LockedDoor implements Informative<Ripley> {
    private String code;


    public CodeDoor(String name, Orientation orientation, String code) {
        super(name, orientation);
        this.code = code;


    }
    public String getCode(){
        return this.code;
    }

    @Override
    public void showInfofor(Ripley actor) {
        actor.setMessage("To open you have to access key with correct code");
    }
}

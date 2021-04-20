package sk.tuke.kpi.oop.game.openables;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.oop.game.Informative;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class LockedDoor extends Door implements Informative<Ripley> {
    private boolean isLocked;
    public LockedDoor(String name,Orientation orientation) {
        super(name,orientation);
        this.isLocked = true;
    }
    public void lock(){
        if(!this.isLocked){
            this.isLocked = true;
            super.close();
        }

    }
    public void unlock(){
        if(this.isLocked){
            this.isLocked = false;
            super.open();
        }

    }
    public boolean isLocked(){
        return this.isLocked;
    }

    @Override
    public void useWith(Actor actor) {
        ;;
    }

    @Override
    public void open() {
        if(!this.isLocked){
            super.open();
        }

    }

    @Override
    public void showInfofor(Ripley actor) {
        actor.setMessage("To open you have to use access card");
    }
}

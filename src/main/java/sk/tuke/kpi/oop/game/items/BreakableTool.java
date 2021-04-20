package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public abstract class BreakableTool<A extends Actor> extends AbstractActor implements Usable<A> {
    private int remainingUses;

    public BreakableTool(int remainingUses) {
        this.remainingUses = remainingUses;
    }

    @Override
    public void useWith(A actor) {
        if (this.remainingUses == 0) {
            getScene().removeActor(this);
            return;
        }
        this.remainingUses -= 1;

        //useless hammer is removed
        if (this.remainingUses == 0) {
            getScene().removeActor(this);
        }

    }

    /*public void use(){
        if (this.remainingUses ==0){
            return;
        }
        this.remainingUses -=1;

        //useless hammer is removed
        if (this.remainingUses ==0){
            getScene().removeActor(this);
        }

    }*/
    public int getRemainingUses() {
        return this.remainingUses;
    }
    public void setRemainingUses(int remainingUses){
        this.remainingUses = remainingUses;
    }
}

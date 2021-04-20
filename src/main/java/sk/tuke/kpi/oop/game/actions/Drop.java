package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop<A extends Keeper> extends AbstractAction<A> {
    @Override
    public void execute(float deltaTime) {
        if(getActor()!=null && this.getActor().getBackpack().peek()!=null ){
            Collectible item =  this.getActor().getBackpack().peek();
            this.getActor().getScene().addActor(item,this.getActor().getPosX(),this.getActor().getPosY());
            this.getActor().getBackpack().remove(item);
        }

        setDone(true);
    }
}



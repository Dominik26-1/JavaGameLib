package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.OnlyOne;


public class Take<A extends Keeper> extends AbstractAction<A> {
    private void oneInPack(OnlyOne<?> newItem){
       for (Collectible item : this.getActor().getBackpack().getContent()){
           if(newItem.getActorClass().isInstance(item)){
               return;
           }
        }
        this.getActor().getBackpack().add((Collectible) newItem);
        this.getActor().getScene().removeActor((Actor) newItem);
    }
    @Override
    public void execute(float deltaTime) {
        try {
            if (this.getActor() != null) {
                for (Actor actor : this.getActor().getScene().getActors()) {
                    if (actor instanceof Collectible && this.getActor().intersects(actor)) {
                        System.out.println(actor);
                        if(actor instanceof OnlyOne){
                            this.oneInPack(((OnlyOne<?>) actor));
                            setDone(true);
                        }
                        else{
                        this.getActor().getBackpack().add((Collectible) actor);
                        this.getActor().getScene().removeActor(actor);
                        }
                    }

                }
            }
            setDone(true);
        }
        catch (Exception ex){
            int windowHeight = this.getActor().getScene().getGame().getWindowSetup().getHeight();
            int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
            this.getActor().getScene().getGame().getOverlay().drawText(ex.getMessage(), 100, yTextPos).showFor(2);
        }
    }
}

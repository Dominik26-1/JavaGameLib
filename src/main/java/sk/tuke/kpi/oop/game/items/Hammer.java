package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Repairable;
import sk.tuke.kpi.oop.game.characters.Ripley;


public class Hammer extends BreakableTool<Repairable> implements Collectible {
    private Animation animation = new Animation("sprites/hammer.png");
    //private int remainingUses;

    public Hammer() {
        super(1);
        setAnimation(animation);
    }


    @Override
    public void useWith(Repairable repairable) {

        boolean isRepaired;
        if(repairable!=null){
            isRepaired =  repairable.repair();
            if(isRepaired){
               Ripley ripley = repairable.getScene().getFirstActorByType(Ripley.class);
               ripley.getBackpack().remove(this);
            }

        }

    }

    @Override
    public Class<Repairable> getUsingActorClass() {
        return Repairable.class;
    }
}



package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher extends BreakableTool<Reactor> implements Collectible{
    private Animation animation = new Animation("sprites/extinguisher.png");


    public FireExtinguisher(){
        super(1);
        setAnimation(animation);

    }

    @Override
    public void useWith(Reactor reactor) {
        if(reactor==null){
            return;
        }
        if(reactor.getTemperature()>=6000){
            reactor.extinguish();
            super.useWith(reactor);
        }

    }

    @Override
    public Class<Reactor> getUsingActorClass() {
        return Reactor.class;
    }
}

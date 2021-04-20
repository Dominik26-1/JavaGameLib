package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;

public class Mjolnir extends Hammer {
    private Animation animation = new Animation("sprites/hammer.png");

    public Mjolnir(){
        super();
        setRemainingUses(4);
        setAnimation(animation);
    }

}

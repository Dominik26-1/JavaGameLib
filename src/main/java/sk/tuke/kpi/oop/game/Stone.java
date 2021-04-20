package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Collectible;



public class Stone extends AbstractActor implements Collectible,Informative<Ripley> {
    private Animation animation = new Animation("sprites/wall_switch.png");
    public Stone() {
        setAnimation(animation);
    }

    @Override
    public void showInfofor(Ripley actor) {
        actor.setMessage("Take and then put on the button with ENTER");
    }

}

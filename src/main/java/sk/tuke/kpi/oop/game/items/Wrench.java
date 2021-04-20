package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool<DefectiveLight> implements Collectible {
    private Animation animation = new Animation("sprites/wrench.png");

    public Wrench() {
        super(2);
        setAnimation(animation);
    }

    @Override
    public void useWith(DefectiveLight actor) {
        //repair defective light
        if (actor != null && !actor.isRepairing() && actor.repair()) {
            super.useWith(actor);


        }

    }

    @Override
    public Class<DefectiveLight> getUsingActorClass() {
        return DefectiveLight.class;
    }
}

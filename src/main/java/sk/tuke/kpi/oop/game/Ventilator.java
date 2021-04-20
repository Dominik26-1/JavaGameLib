package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;

public class Ventilator extends AbstractActor implements Repairable {
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("ventilator repaired",
       Ventilator.class);
    private boolean isDamaged;
    private Animation animation = new Animation("sprites/ventilator.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG);
    public Ventilator() {
        this.isDamaged = true;
        setAnimation(animation);
        this.animation.pause();
    }

    @Override
    public boolean repair() {
        if(this.isDamaged){
            this.isDamaged = false;
            this.getAnimation().play();
            this.getScene().getMessageBus().publish(VENTILATOR_REPAIRED,this);
            return true;
        }
        return false;
    }
}

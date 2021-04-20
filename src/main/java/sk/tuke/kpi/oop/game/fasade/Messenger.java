package sk.tuke.kpi.oop.game.fasade;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;


public class Messenger extends AbstractActor {
    private Animation animation = new Animation("sprites/invisible.png");
    private Sendable<Ripley> laser;
    private Sendable<Ripley> electricity;
    private Ripley ripley;
    public Messenger() {
        setAnimation(animation);
    }
    public void laserSending(){
        this.laser.sendMessage(ripley);
    }
    public void electricitySending(){
        this.electricity.sendMessage(ripley);
    }
    public void setSenders(Sendable<Ripley> laser,Sendable<Ripley> electricity){
        this.laser = laser;
        this.electricity = electricity;
    }
    public void setReceiver(Ripley ripley){
        this.ripley = ripley;
    }
}

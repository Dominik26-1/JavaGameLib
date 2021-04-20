package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer{
    private Animation animationOn = new Animation("sprites/light_on.png");
    private Animation animationOff = new Animation("sprites/light_off.png");
    private boolean isOn;
    private boolean isPowered;

    public Light(){
        this.isOn = false;
        setAnimation(animationOff);
    }

    public  void updateAnimation(){
        if(this.isPowered && this.isOn){
            setAnimation(animationOn);
        }
        else{
            setAnimation(animationOff);
        }
    }
    public boolean isOn(){
        return isOn;
    }
    public void toggle(){
        //switch light on and off
        if (this.isOn==true){
            turnOff();
        }
        else {
            turnOn();

        }
    }

    @Override
    public void turnOn() {
        this.isOn=true;
        this.updateAnimation();

    }

    @Override
    public void turnOff() {
        this.isOn = false;
        this.updateAnimation();

    }

    public void setPowered(boolean isPowered){
        //set value to variable
        this.isPowered = isPowered;
        this.updateAnimation();
    }


}

package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.graphics.Color;

public class PowerSwitch extends AbstractActor{
    private Animation animation = new Animation("sprites/switch.png");
    private Switchable device;

    public PowerSwitch(Switchable device){
        this.device = device;
        setAnimation(animation);

    }

    //poskytuje referenciu na pripojené zariadenie
    public Switchable getDevice(){
        return device;

    };
    public void setDevice(Switchable device){
        this.device = device;
    }
    //zapína pripojené zariadenie
    public void switchOn(){
        if(device!=null){
            device.turnOn();
            this.getAnimation().setTint(Color.WHITE);
        }
    };
    //vypína pripojené zariadenie
    public void switchOff(){
        if(device!=null){
            device.turnOff();
            this.getAnimation().setTint(Color.GRAY);
        }


    };


}

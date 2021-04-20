package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable,Repairable{
    private int temperature;
    private int damage;
    private final Animation normalAnimation = new Animation("sprites/reactor_on.png", 80,
        80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation hotReactor = new Animation("sprites/reactor_hot.png", 80,
        80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation brokenReactor = new Animation("sprites/reactor_broken.png", 80,
        80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private final Animation turnOffReactor = new Animation("sprites/reactor.png");
    private final Animation extinguishedReactor = new Animation("sprites/reactor_extinguished.png");
    private boolean running_status;
    //private EnergyConsumer device;
    private Set<EnergyConsumer> devices;


    public Reactor() {
        this.temperature = 0;
        this.damage = 0;
        this.running_status = false;
        setAnimation(turnOffReactor);
        //this.device = null;
        this.devices = new HashSet<>();

    }

    public int getTemperature() {
        return this.temperature;
    }

    public int getDamage() {
        return this.damage;
    }

    public void increaseTemperature(int increment) {
        //check if reactor is on and check valid increment
        if (!this.running_status || increment<0) {
            return;
        }
        //check broken reactor
        if (this.temperature >= 6000) {
            return;
        }

        //set new temperature
        if (this.damage < 33) {
            this.temperature += increment;
        } else if (this.damage <= 66) {
            this.temperature += (int) Math.ceil(1.5 * increment);
        } else {
            this.temperature += 2 * increment;
        }

        //calculate damage of reactor
        if (this.temperature > 2000 && this.temperature < 6000) {
            this.damage = (int) Math.floor((0.025 * this.temperature) - 50);

        }
        //reactor is broken
        if (this.temperature >= 6000) {
            this.damage = 100;
            running_status = false;

        }
        //set reactor animation
        this.updateAnimation();

    }

    public void decreaseTemperature(int decrement) {
        //check if reactor is on and check valid decrement
        if (!this.running_status || this.temperature < 0 || decrement<0) {
            return;
        }
        //max damage nothing happens
        if (this.damage == 100) {
            return;
        } else if (this.damage >= 50) {
            this.temperature -= (int) Math.ceil(0.5 * decrement);
        } else {
            this.temperature -= decrement;
        }
        this.updateAnimation();
    }

    private void updateAnimation() {
        //set image for turn off reactor
        if (!this.running_status && this.damage != 100) {
            setAnimation(turnOffReactor);
            this.checkDevice();
            return;
        }
        //set image for broken turn off reactor
        if (!this.running_status && this.damage == 100) {
            setAnimation(brokenReactor);
            this.checkDevice();
            return;
        }
        //set image for turn on reactor according to temperature
        if (this.temperature <= 4000) {
            setAnimation(normalAnimation);
        } else if (this.temperature < 6000) {
            setAnimation(hotReactor);
        } else {
            setAnimation(brokenReactor);
        }
        //set animation for devices
        this.checkDevice();
    }

    public boolean repair(){
        if (this.damage == 0 || this.damage == 100) {
            return false;
        }
        if(this.getTemperature()>=6000 || this.getDamage()==0){
            return false;
        }
        //correct use
        //hammer.use();

        //calculate new temperature
        int temp_damage = this.damage - 50;
        int temp_temperature = 40 * temp_damage + 2000;
        if (this.temperature > temp_temperature) {
            this.temperature = temp_temperature;
        }


        if (this.damage < 50) {
            this.damage = 0;
        } else {
            this.damage -= 50;
        }
        //set correct temperature in the future
        //set animation of reactor
        this.updateAnimation();
        return true;
    }

    /*public void repairWith(Hammer hammer) {
        //impossible use
        if (hammer == null || hammer.getRemainingUses() == 0 || this.damage == 0 || this.damage == 100) {
            return;
        }
        //correct use
        hammer.use();

        //calculate new temperature
        int temp_damage = this.damage - 50;
        int temp_temperature = 40 * temp_damage + 2000;
        if (this.temperature > temp_temperature) {
            this.temperature = temp_temperature;
        }


        if (this.damage < 50) {
            this.damage = 0;
        } else {
            this.damage -= 50;
        }
        //set correct temperature in the future
        //set animation of reactor
        this.updateAnimation();
    }*/

    public boolean extinguish(){
        //set new temperature
        if (this.temperature >= 6000) {
            //fireExtinguisher.use();
            this.temperature = 4000;
            this.setAnimation(extinguishedReactor);
            return true;
        }
        return false;
    }

    public void extinguishWith(FireExtinguisher fireExtinguisher) {
        //check if is possible to use
        if (fireExtinguisher == null || fireExtinguisher.getRemainingUses() == 0) {
            return;
        }
        //set new temperature
        if (this.temperature >= 6000) {
            //fireExtinguisher.use();
            this.temperature = 4000;
            this.setAnimation(extinguishedReactor);
        }
    }

    //turn on / turn off reactor
    public void turnOn() {
        if(this.getDamage()==100){
            return;
        }
        this.running_status = true;
        this.updateAnimation();

    }

    public void turnOff() {
        this.running_status = false;
        this.updateAnimation();

    }

    public boolean isOn() {
        return this.running_status;
    }

    public void addDevice(EnergyConsumer device) {
        //add device in array of other connected devices
        this.devices.add(device);
        if(this.running_status!=false && this.damage<100){
            device.setPowered(true);
        }
        else{
            device.setPowered(false);
        }




    }


    public void removeDevice(EnergyConsumer device) {
        //remove device from other connected devices
        this.devices.remove(device);
        device.setPowered(false);


    }

    private void checkDevice() {
        //check if reactor is connected with light
        if (this.devices == null) {
            return;
        }
        //set animation for each device which are connected
        for (EnergyConsumer device : this.devices) {
            if (this.damage != 100 && this.running_status == true) {
                device.setPowered(true);
            }
            else{
                device.setPowered(false);
            }

        }
    }



    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        //set automatically increasing temperature
        scene.scheduleAction(new PerpetualReactorHeating(5), this);
        new Loop<>(new Invoke<>(this::checkDevice)).scheduleFor(this);

    }

}





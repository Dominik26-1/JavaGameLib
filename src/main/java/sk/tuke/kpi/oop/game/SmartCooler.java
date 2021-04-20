package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class SmartCooler extends Cooler {
    private Reactor reactor;

    public SmartCooler(Reactor reactor) {
        super(reactor);
        this.reactor = reactor;
    }
    private void checkSmartCooler(){
        //smart turn on or off cooler
        if (this.reactor.getTemperature() >= 2500) {
            if(!this.isOn() && null != this.reactor){
                this.reactor.increaseTemperature(1);
            }
            this.turnOn();
        }
        if (this.reactor.getTemperature() <= 1500) {
            if(this.isOn()){
                this.reactor.decreaseTemperature(1);
            }

            this.turnOff();
        }
    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        if (this.reactor != null) {
            super.addedToScene(scene);
            new Loop<>(new Invoke<>(this::checkSmartCooler)).scheduleFor(this);

        }
    }

}

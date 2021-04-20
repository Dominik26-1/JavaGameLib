package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Button extends PowerSwitch implements Enable,Informative<Ripley> {
    private boolean isAvailable;
    private Animation animation = new Animation("sprites/button_green.png");
    public Button(Switchable device) {
        super(device);
        setAnimation(animation);
        this.isAvailable = false;
    }


    @Override
    public void switchOn() {
        if(isAvailable){
            super.switchOn();
        }
    }

    @Override
    public void switchOff() {
        if(isAvailable){
            super.switchOff();
        }

    }

    @Override
    public void setEnable() {
        this.isAvailable= true;
        this.getDevice().turnOff();
    }

    @Override
    public void setUnable() {
        this.isAvailable= false;
        this.getDevice().turnOn();

    }

    public void pressSomething(){
        for (Actor a :this.getScene().getActors()){
            if (a instanceof Stone && a.intersects(this)){
                this.setEnable();
                return;
            }
            else if(a instanceof Stone && !a.intersects(this)) {
                this.setUnable();
                return;

            }
        }
        this.setUnable();

    }
    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::pressSomething)).scheduleFor(this);
    }

    @Override
    public void showInfofor(Ripley actor) {
        actor.setMessage("Turn off laser with putting stone on me!");
    }
}

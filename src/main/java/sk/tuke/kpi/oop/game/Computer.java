package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {
    private boolean isPowered;
    public Computer() {
        Animation normalAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        this.isPowered = false;
    }
    //computer works
    public int add(int a, int b) {
        if (this.isPowered) {
            return (a + b);
        }
        return 0;
    }

    public float add(float a, float b) {
        if (this.isPowered) {
            return a + b;
        }
        return 0;
    }

    public int sub(int a, int b) {
        if (this.isPowered) {
            return a - b;
        }
        return 0;
    }

    public float sub(float a, float b) {
        if (this.isPowered) {
            return a - b;
        }
        return 0;
    }

    @Override
    public void setPowered(boolean isPowered) {
        //check if it is connected to reactor
        this.isPowered = isPowered;
        if (isPowered) {
            this.getAnimation().play();
        }
        else{
            this.getAnimation().pause();
        }
    }
}

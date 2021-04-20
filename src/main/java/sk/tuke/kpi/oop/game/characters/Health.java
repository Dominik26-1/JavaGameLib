package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {
    private int actualValue,maxValue;
    private boolean isDead;
    private List<ExhaustionEffect> effects;
    public Health(int actualValue, int maxValue) {
        this.maxValue = maxValue;
        this.actualValue = actualValue;
        this.effects = new ArrayList<>();
        this.isDead = false;
    }

    public Health(int Value) {
        this.actualValue = Value;
        this.maxValue = Value;
        this.effects = new ArrayList<>();
        this.isDead = false;
    }

    public int getValue(){
        return this.actualValue;
    }
    public void refill(int amount){
        if(this.actualValue+amount<=this.maxValue){
            this.actualValue += amount;
            return;
        }
        this.actualValue = this.maxValue;
    }
    public void restore(){
        this.actualValue = this.maxValue;
    }
    public void drain(int amount){
        if(this.actualValue-amount>0){
            this.actualValue -=amount;
        }
        else{
            this.exhaust();
        }
    }
    public void exhaust(){
        this.actualValue = 0;
        this.effectsApply();
    }
    private void effectsApply(){
        if(!this.isDead) {
            for (ExhaustionEffect oneEffect : this.effects) {
                oneEffect.apply();
            }
            this.isDead = true;
        }
    }
    public void onExhaustion(ExhaustionEffect effect){
        this.effects.add(effect);
    }
    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

}

package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable {
    private Disposable disposable;
    private boolean repairingStatus;

    public DefectiveLight() {
        super();
        this.repairingStatus = false;
    }

    //set unexpected behaviour and turn on light randomly
    public void uncontrolledBehaviour() {
        int rand = (int) (Math.random() * 20) + 1;
        if (rand == 1 || rand == 2) {
            super.toggle();
        }
    }
    public boolean isRepairing(){
        return this.repairingStatus;
    }
    private void setIsRepairing(boolean is){
        this.repairingStatus = is;
    }


    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.disposable =
            new Loop<>(new Invoke<>(this::uncontrolledBehaviour)
            ).scheduleFor(this);

    }

    @Override
    public boolean repair() {
        if(this.repairingStatus){
            return false;
        }
        //set defective light to light for 10 sec regularly
        //this.turnOn();
        this.repairingStatus = true;
        this.disposable.dispose();
        this.disposable = new ActionSequence<>(
            new Invoke<>(() -> this.setIsRepairing(true)),
            new Wait<>(10),
            new Invoke<>(() -> this.setIsRepairing(false)),
            new Loop<>(new Invoke<>(this::uncontrolledBehaviour))
        ).scheduleFor(this);
        return true;
    }
}

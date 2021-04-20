package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Reactor;

public class PerpetualReactorHeating extends AbstractAction<Reactor> {
    private int temperatureIncrement;

    public PerpetualReactorHeating(int increment) {
        this.temperatureIncrement = increment;
    }

    @Override
    public void execute(float deltaTime) {
        //set automatically increasing temperature about 1
        Reactor reactor = getActor();
        if(reactor!=null){
            reactor.increaseTemperature(temperatureIncrement);
        }
    }
}

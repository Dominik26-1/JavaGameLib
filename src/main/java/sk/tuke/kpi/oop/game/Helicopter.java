package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor {
    private Animation animation = new Animation("sprites/heli.png", 64, 64, 0.2f,
        Animation.PlayMode.LOOP_PINGPONG);
    private Player player;


    public Helicopter() {
        setAnimation(animation);
    }

    public void searchAndDestroy() {
        if(this.player==null){
            this.player = getScene().getFirstActorByType(Player.class);
        }
        new Loop<>(new Invoke<>(this::setSpy)).scheduleFor(this);

    }

    public void setSpy() {
        int currentPosX = getPosX();
        int currentPosY = getPosY();
        if (player == null) {
            return;
        }
        if (this.getPosX() > player.getPosX()) {
            currentPosX--;
        } else {
            currentPosX++;
        }
        if (this.getPosY() > player.getPosY()) {
            currentPosY--;
        } else {
            currentPosY++;
        }

        this.setPosition(currentPosX, currentPosY);

        if (this.intersects(player)) {
            player.setEnergy(player.getEnergy() - 1);
        }


    }

}

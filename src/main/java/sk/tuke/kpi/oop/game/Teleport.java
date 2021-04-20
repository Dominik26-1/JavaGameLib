package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;

import java.awt.geom.Rectangle2D;

public class Teleport extends AbstractActor {
    private Animation animation = new Animation("sprites/lift.png");
    private Teleport target;
    private boolean teleportMove;

    public Teleport(Teleport next) {
        this.target = next;
        setAnimation(animation);
        this.teleportMove = false;
    }
    public Teleport(String name, Teleport next) {
        super(name);
        this.target = next;
        setAnimation(animation);
        this.teleportMove = false;
    }

    public Teleport getDestination() {
        if (this.target != null) {
            return this.target;
        }
        return null;
    }

    public void setDestination(Teleport destinationTeleport) {
        if (destinationTeleport == null) {
            this.target = null;
            return;
        }
        if (!destinationTeleport.equals(this)) {
            this.target = destinationTeleport;
        }

    }

    public void teleportPlayer(Ripley player) {
        getScene().removeActor(player);

        player.setPosition(this.getPosX() + this.getWidth() / 2 - player.getWidth() / 2,
            this.getPosY() + this.getHeight() / 2 - player.getHeight() / 2);
        this.teleportMove = false;
        if (this.target!=null){
            this.target.teleportMove = false;
        }

        getScene().addActor(player);



    }

    public void checkCoordinates(Ripley player) {
        if (player == null || this.target == null) {
            return;
        }
        Rectangle2D playerArea = new Rectangle2D.Float(player.getPosX(), player.getPosY(), player.getWidth(),
            player.getHeight());
        Rectangle2D teleportArea = new Rectangle2D.Float(this.getPosX(), this.getPosY(), this.getWidth(),
            this.getHeight());
        if(!teleportArea.intersects(playerArea.getCenterX(), playerArea.getCenterY(), 1, 1)&& !this.teleportMove){
            this.teleportMove = true;
        }

        if (teleportArea.intersects(playerArea.getCenterX(), playerArea.getCenterY(), 1, 1)&& this.teleportMove) {
            this.target.teleportPlayer(player);
        }

    }


    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        Ripley player = getScene().getFirstActorByType(Ripley.class);
        new Loop<>(new Invoke<>(() -> this.checkCoordinates(player))
        ).scheduleFor(this);
    }
}

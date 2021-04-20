package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Informative;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Usable;

public class Door extends AbstractActor implements Openable, Usable<Actor>, Informative<Ripley> {

    public enum Orientation {HORIZONTAL, VERTICAL}
    private Orientation shapeDoor;
    private MapTile tile1, tile2;
    private boolean isOpen;
    private Animation vdoor = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE_REVERSED);
    private Animation hdoor = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE_REVERSED);

    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);

    public Door(String name,Orientation orientation) {
        super(name);
        if (orientation == Orientation.HORIZONTAL) {
            setAnimation(hdoor);
            this.shapeDoor = Orientation.HORIZONTAL;
        } else {
            setAnimation(vdoor);
            this.shapeDoor = Orientation.VERTICAL;
        }
        this.isOpen = false;

    }

    @Override
    public void useWith(Actor actor) {
        if (this.isOpen) {
            this.close();
        } else {
            this.open();
        }

    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }

    @Override
    public void open() {
        if (!isOpen) {
            this.tile1.setType(MapTile.Type.CLEAR);
            this.tile2.setType(MapTile.Type.CLEAR);
            this.getAnimation().setPlayMode(Animation.PlayMode.ONCE);
            this.getScene().getMessageBus().publish(DOOR_OPENED, this);
        }

        this.isOpen = true;

    }

    @Override
    public void close() {
        if (isOpen) {
            this.tile1.setType(MapTile.Type.WALL);
            this.tile2.setType(MapTile.Type.WALL);
            this.getAnimation().setPlayMode(Animation.PlayMode.ONCE_REVERSED);
            this.getScene().getMessageBus().publish(DOOR_CLOSED, this);
        }

        this.isOpen = false;
    }

    @Override
    public boolean isOpen() {
        if (this.isOpen) {
            return true;
        }
        return false;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        if (this != null) {
            if(this.shapeDoor==Orientation.HORIZONTAL){
                this.tile1 = this.getScene().getMap().getTile(this.getPosX() / 16 + 1, this.getPosY() / 16);

            }
            if (this.shapeDoor==Orientation.VERTICAL){
                this.tile1 = this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16 + 1);
            }

            this.tile2 = this.getScene().getMap().getTile(this.getPosX() / 16, this.getPosY() / 16);
            this.tile1.setType(MapTile.Type.WALL);
            this.tile2.setType(MapTile.Type.WALL);
        }
    }

    @Override
    public void showInfofor(Ripley actor) {
        if (actor!=null && this.getName().equals("back ndoor")){
            actor.setMessage("Repair ventilator and kill all alien");
        }
    }
}

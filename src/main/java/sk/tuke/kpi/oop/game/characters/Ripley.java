package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;

public class Ripley extends AbstractActor implements Movable, Keeper, Alive, Armed, Visible {
    private Animation animation = new Animation("sprites/player.png", 32, 32, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
    private Animation diedAnimation = new Animation("sprites/player_die.png", 32, 32, 0.1f, Animation.PlayMode.ONCE);
    private Disposable disposable;
    private int speed;
    private int ammo;
    private String message;
    private boolean isVisible;
    private Backpack backpack;
    private Health health;
    private Firearm gun;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("ripley died", Ripley.class);

    public Ripley() {
        super("Ellen");
        setAnimation(animation);
        animation.pause();
        this.speed = 2;
        this.ammo = 0;
        this.health = new Health(50, 100);
        this.health.onExhaustion(this::onExhaustion);
        this.backpack = new Backpack("Ripley's backpack", 10);
        this.gun = new Gun(100, 100);
        this.isVisible = true;

    }


    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }

    public int getAmmo() {
        return this.ammo;
    }


    public void decreaseEnergy() {
        this.disposable =
            new Loop<>(
                new ActionSequence<>(
                    new Wait<>(1),
                    new Invoke<>(() -> this.health.drain(1))
                    //new Invoke<>(() -> this.onExhaustion())
                )).scheduleFor(this);

    }

    public void stopDecreasing() {
        if(this.disposable!=null){
            this.disposable.dispose();
        }

    }

    private void onExhaustion() {
        setAnimation(diedAnimation);
        getScene().cancelActions(this);
        getScene().getMessageBus().publish(RIPLEY_DIED, this);
    }


    public void showRipleyState() {
        int windowHeight = this.getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        this.getScene().getGame().getOverlay().drawText(" | Energy: " + this.health.getValue(), 100, yTextPos);
    }

    public void showRipleyState(String message) {
        int windowHeight = this.getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        if (this.message != null) {
            this.getScene().getGame().getOverlay().drawText("Instruction: "  + this.message, 10, yTextPos - 30);

        }
        if (message != null) {
            this.getScene().getGame().getOverlay().drawText(" | Energy: " + this.health.getValue() + " | Ammo: " + this.gun.getAmmo() + "    " + message, 100, yTextPos);
            return;
        }
        this.getScene().getGame().getOverlay().drawText(" | Energy: " + this.health.getValue() + " | Ammo: " + this.gun.getAmmo(), 100, yTextPos);

    }


    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public int getSpeed() {
        return this.speed;
    }

    @Override
    public void startedMoving(Direction direction) {
        this.animation.setRotation(direction.getAngle());
        this.animation.play();
    }

    @Override
    public void stoppedMoving() {
        this.animation.pause();
    }

    @Override
    public Backpack getBackpack() {
        return this.backpack;
    }

    @Override
    public Health getHealth() {
        return this.health;
    }

    @Override
    public Firearm getFirearm() {
        return this.gun;
    }

    @Override
    public void setFirearm(Firearm weapon) {
        this.gun = weapon;
    }

    @Override
    public boolean isVisible() {
        return this.isVisible;
    }

    @Override
    public void setInvisible() {
        this.isVisible = false;
        this.getAnimation().setAlpha(0.5f);
    }

    @Override
    public void setVisible() {
        this.isVisible = true;
        this.getAnimation().setAlpha(1);

    }

}

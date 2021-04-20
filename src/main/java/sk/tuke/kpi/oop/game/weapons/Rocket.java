package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Enemy;
public class Rocket extends Bullet {
    private Animation animation = new Animation("sprites/rocket.png");
    public Rocket() {
        super();
        setAnimation(animation);
    }

    @Override
    public void checkColision() {
        for (Actor actor: this.getScene().getActors()){
            if(actor instanceof Alive && this.intersects(actor) && !(actor instanceof Enemy)){
                ((Alive) actor).getHealth().drain(10);
                this.stoppedMoving();
                this.getScene().cancelActions(this);
                this.getScene().removeActor(this);


            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        this.animation.setRotation(90);
    }
}

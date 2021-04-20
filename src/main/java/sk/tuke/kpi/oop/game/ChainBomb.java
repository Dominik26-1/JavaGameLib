package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

public class ChainBomb extends TimeBomb {
    private Set<ChainBomb> arrayChainBomb;

    public ChainBomb(float time) {
        super(time);
        this.arrayChainBomb = new HashSet<>();
    }


    private void addBomb() {
        //add device in array of other connected devices

        for (Actor actor : getScene().getActors()) {
            if (actor instanceof ChainBomb && actor!=this) {
                arrayChainBomb.add((ChainBomb) actor);
            }
        }

    }

    @Override
    public void explode() {
        this.checkChainBombs();
        super.explode();
    }

    public void checkChainBombs() {
        Ellipse2D circle = new Ellipse2D.Float(this.getPosX()+this.getWidth()/2-50,
            this.getPosY()+(this.getHeight()/2-50), 100,100);
        for (ChainBomb bomb : this.arrayChainBomb) {
            Rectangle2D bombArea = new Rectangle2D.Float(bomb.getPosX(),bomb.getPosY(),
                bomb.getWidth(),bomb.getHeight());
            if (circle.intersects(bombArea) && !bomb.isActivated()){
                bomb.activate();
            }
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new ActionSequence<>(
            new Invoke<>(this::addBomb)
        )).scheduleFor(this);
    }
}

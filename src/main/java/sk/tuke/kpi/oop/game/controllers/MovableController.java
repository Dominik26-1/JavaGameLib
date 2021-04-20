package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {
    private Movable actor;
    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.LEFT, Direction.WEST),
        Map.entry(Input.Key.RIGHT, Direction.EAST)
    );
    private Set<Direction> directionSet;
    private Move<Movable> lastMove;
    private Disposable disposable;
    private Direction finalDirection;

    public MovableController(Movable actor) {
        this.actor = actor;
        this.finalDirection = Direction.NONE;
        this.directionSet = new HashSet<>();
    }
    public void resetDirection(){
        this.lastMove.stop();
        this.disposable.dispose();
        this.lastMove = null;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {
        if (this.keyDirectionMap.containsKey(key)) {
            if(this.lastMove!=null){
                this.resetDirection();
            }
            this.directionSet.add(this.keyDirectionMap.get(key));
            for (Direction d : this.directionSet) {
                this.finalDirection = this.finalDirection.combine(d);

                //System.out.println(this.finalDirection);
            }

            this.lastMove = new Move<>(this.finalDirection);
            this.disposable = this.lastMove.scheduleFor(actor);
        }


    }


    @Override
    public void keyReleased(@NotNull Input.Key key) {
        if (this.keyDirectionMap.containsKey(key)) {
            if (this.lastMove != null) {
                this.resetDirection();
            }

            this.directionSet.remove(this.keyDirectionMap.get(key));
            this.finalDirection = Direction.NONE;
            for (Direction d : this.directionSet) {
                this.finalDirection = this.finalDirection.combine(d);
            }

            this.lastMove = new Move<>(this.finalDirection);
            this.disposable = this.lastMove.scheduleFor(actor);


        }
    }
}


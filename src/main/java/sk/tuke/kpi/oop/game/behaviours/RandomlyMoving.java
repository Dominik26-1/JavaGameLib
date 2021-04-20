package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import java.util.Map;

public class RandomlyMoving implements Behaviour<Movable> {
    private Map<Integer, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(0, Direction.NORTH),
        Map.entry(1, Direction.NORTHEAST),
        Map.entry(2, Direction.EAST),
        Map.entry(3, Direction.SOUTHEAST),
        Map.entry(4, Direction.SOUTH),
        Map.entry(5, Direction.SOUTHWEST),
        Map.entry(6, Direction.WEST),
        Map.entry(7, Direction.NORTHWEST)
    );
    public RandomlyMoving() {
    }

    @Override
    public void setUp(Movable actor) {
        if(actor!=null){
            int randomNumber = (int)(Math.random()*7+0);
            new Move<>(keyDirectionMap.get(randomNumber)).scheduleFor(actor);


        }
    }

}

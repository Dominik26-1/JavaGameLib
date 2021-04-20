package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class ChaisingMoving implements Behaviour<Movable> {
    public ChaisingMoving() {
    }

    @Override
    public void setUp(Movable actor) {
        if(actor==null){
           return;
        }
        Ripley ripley = actor.getScene().getFirstActorByType(Ripley.class);
        if(ripley==null){
            return;
        }
        int xPosDiff = actor.getPosX() - ripley.getPosX();
        int yPosDiff = actor.getPosY() - ripley.getPosY();
        Direction correctMove;
        if(xPosDiff>0){
            correctMove = Direction.WEST;
        }
        else if(xPosDiff==0){
            correctMove = Direction.NONE;
        }
        else{
            correctMove = Direction.EAST;
        }
        if(yPosDiff>0) {
            correctMove = correctMove.combine(Direction.SOUTH);
        }
        else if(yPosDiff==0){
            correctMove = correctMove.combine(Direction.NONE);
        }
        else{
            correctMove = correctMove.combine(Direction.NORTH);
        }
        new Move<>(correctMove,1).scheduleFor(actor);


    }
}

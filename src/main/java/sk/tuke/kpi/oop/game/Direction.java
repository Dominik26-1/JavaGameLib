package sk.tuke.kpi.oop.game;

import static java.lang.StrictMath.atan2;
import static java.lang.StrictMath.toDegrees;

public enum Direction {
    NORTH(0, 1),
    EAST(1, 0),
    SOUTH(0, -1),
    WEST(-1, 0),
    NORTHWEST(-1, 1),
    SOUTHWEST(-1, -1),
    NORTHEAST(1, 1),
    SOUTHEAST(1, -1),
    NONE(0, 0);
    private final int dx;
    private final int dy;


    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
    }

    public float getAngle() {
        float angle = (float) toDegrees(atan2(this.dx, this.dy));
        if (angle != 0) {
            return -angle;
        }
        return angle;
    }
    public static Direction fromAngle(float angle){
        switch ((int) angle) {
            case 0:
                return NORTH;
            case 90:
                return WEST;
            case 180:
                return SOUTH;
            case 270:
                return EAST;
            case 45:
                return NORTHWEST;
            case 135:
                return SOUTHWEST;
            case 225:
                return SOUTHEAST;
            case 315:
                return NORTHEAST;
            default:
                return NONE;
        }
    }

    public Direction combine(Direction other) {
        if (other != null) {
            int new_dx = this.getDx() + other.getDx();
            int new_dy = this.getDy() + other.getDy();
            if(new_dx>1){
                new_dx=1;
            }
            if(new_dx<-1){
                new_dx =-1;
            }
            if(new_dy>1){
                new_dy = 1;
            }
            if(new_dy<-1){
                new_dy = -1;
            }

            for (Direction d: Direction.values()){
                if(d.getDy()==new_dy && d.getDx()==new_dx){
                    return d;
                }
            }
            return NONE;
        }
        return NONE;

    }
}


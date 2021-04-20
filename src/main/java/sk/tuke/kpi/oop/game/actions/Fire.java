package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Engine;
import sk.tuke.kpi.oop.game.weapons.Fireable;


public class Fire<A extends Armed & Actor> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {
        if(this.getActor()!=null){
            if(this.getActor().getFirearm().getAmmo()==0){
                setDone(true);
                return;
            }
            Fireable bullet = this.getActor().getFirearm().fire();
            this.getActor().getScene().addActor(bullet,this.getActor().getPosX()+
                    this.getActor().getWidth()/2-bullet.getWidth()/2,
                this.getActor().getPosY()+this.getActor().getHeight()/2- bullet.getHeight()/2);
            float angle = getActor().getAnimation().getRotation();
            if(getActor() instanceof Engine){
                angle = 90.0f;
                this.getActor().getScene().removeActor(bullet);
                if(this.getActor().getAnimation().getCurrentFrameIndex()==0){
                    this.getActor().getScene().addActor(bullet,this.getActor().getPosX(),this.getActor().getPosY()+
                        this.getActor().getHeight()/4-bullet.getHeight()/2);
                }
                else{
                    this.getActor().getScene().addActor(bullet,this.getActor().getPosX(),this.getActor().getPosY()+
                        (this.getActor().getHeight()*3/4)-bullet.getHeight()/2);
                }



            }
            while (angle<0){
                angle +=360;
            }
            new Move<Fireable>(Direction.fromAngle(angle)).scheduleFor(bullet);

            setDone(true);
        }
        setDone(true);
    }
}

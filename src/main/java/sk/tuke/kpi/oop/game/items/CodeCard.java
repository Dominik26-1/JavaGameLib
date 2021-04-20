package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Informative;
import sk.tuke.kpi.oop.game.actions.Info;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.openables.CodeDoor;
import sk.tuke.kpi.oop.game.openables.Door;

public class CodeCard extends AbstractActor implements Usable<CodeDoor>,Collectible, OnlyOne<CodeCard>, Informative<Ripley> {
    private String cardCode;
    private Animation animation = new Animation("sprites/key.png",16,16);
    public static final Topic<CodeCard> INCORRECT_CODE = Topic.create("incorrect code", CodeCard.class);

    public CodeCard(String code) {
        setAnimation(animation);
        this.cardCode = code;
    }


    @Override
    public void useWith(CodeDoor actor) {
        if (actor.getCode().equals(this.cardCode)){
            if(actor.isLocked()){
                actor.unlock();
            }
            else{
                actor.lock();
            }
        }else{
            this.getScene().getMessageBus().publish(INCORRECT_CODE, this);
        }

    }

    @Override
    public void showInfofor(Ripley actor) {
        actor.setMessage("My code is "+this.cardCode);
    }

    @Override
    public Class<CodeDoor> getUsingActorClass() {
        return CodeDoor.class;
    }

    @Override
    public Class<CodeCard> getActorClass() {
        return CodeCard.class;
    }
}


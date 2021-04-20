package sk.tuke.kpi.oop.game.weapons;

public class RocketGun extends Firearm {
    public RocketGun(int bullets) {
        super(bullets);
    }

    @Override
    protected Fireable createBullet() {
        return new Rocket();
    }
}

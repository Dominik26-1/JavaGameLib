package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm{
    public Gun(int bullets, int maxBullets) {
        super(bullets, maxBullets);
    }

    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }
}

package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm {
    private int bullets, maxBullets;
    public Firearm(int bullets, int maxBullets) {
        this.bullets = bullets;
        this.maxBullets = maxBullets;
    }
    public Firearm(int bullets){
        this.bullets = bullets;
        this.maxBullets = bullets;
    }
    public int getAmmo(){
        return this.bullets;
    }
    public int getMaxBullets(){
        return this.maxBullets;
    }
    public void reload(int newAmmo){
        if(this.bullets+newAmmo>this.maxBullets){
            this.bullets = this.maxBullets;
        }
        else{
            this.bullets += newAmmo;
        }
    }
    public Fireable fire(){
        if(this.bullets>0){
            this.bullets--;
            return createBullet();
        }
        else{
            return null;
        }
    }
    protected abstract Fireable createBullet();

}

package sk.tuke.kpi.oop.game.builder;

public class LockerCompany implements Company {

    @Override
    public void build(Builder builder) {
        builder.createItem();
        builder.setUses();

    }
}

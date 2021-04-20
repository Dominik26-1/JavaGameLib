package sk.tuke.kpi.oop.game;

public interface Switchable {
    //metóda zapne ovládané zariadenie
    void turnOn();
    // metóda vypne ovládané zariadenie
    void turnOff();
    //metóda vráti hodnotu, ktorá reprezentuje stav zariadenia
    // (true - zariadenie je zapnuté, false - zariadenie je vypnuté)
    boolean isOn();
}

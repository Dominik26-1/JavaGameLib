package sk.tuke.kpi.oop.game.items;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.ActorContainer;

import java.util.*;

public class Backpack implements ActorContainer<Collectible> {
    private String name;
    private int capacity;
    private List<Collectible>content = new ArrayList<>();
    private int size;
    public Backpack(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public @NotNull List<Collectible> getContent() {
        List<Collectible> copyContent = new ArrayList<>();
        for (Collectible item : this.content) {
            copyContent.add(item);
        }
        return copyContent;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public void add(@NotNull Collectible actor) {
        if(this.size>=this.capacity){
            throw new IllegalStateException(this.name + "is full");
        }
        this.content.add(actor);
        this.size +=1;
    }

    @Override
    public void remove(@NotNull Collectible actor) {
        this.content.remove(actor);
        this.size -=1;
    }

    @NotNull
    @Override
    public Iterator<Collectible> iterator() {
        return this.content.iterator();
    }

    @Override
    public @Nullable Collectible peek() {
        if(this.size==0){
            return null;
        }
        return this.content.get(this.size-1);
    }

    @Override
    public void shift() {
        Collections.rotate(content,1);
    }

}


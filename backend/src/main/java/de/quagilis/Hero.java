package de.quagilis;

public class Hero {
    public int id;
    public String name;
    public Power power;
    public String alterEgo;

    public Hero(int id, String name, Power power) {
        this(id, name, power, null);
    }

    public Hero(int id, String name, Power power, String alterEgo) {
        this.id = id;
        this.name = name;
        this.power = power;
        this.alterEgo = alterEgo;
    }

}

package simulator.organisms;

import simulator.organisms.animals.*;
import simulator.organisms.plants.*;
import tools.Point;
import simulator.World;

import java.awt.*;

public abstract class Organism implements java.io.Serializable {
    protected int strength;
    protected int initiative;
    protected int age;
    protected World world;
    protected Point location;

    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public int getInitiative() {
        return initiative;
    }
    public int getAge() {
        return age;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Organism(int strength, int initiative, World world, Point location) {
        this.strength = strength;
        this.initiative = initiative;
        this.age = 0;
        this.world = world;
        this.location = location;
    }

    public abstract void action();
    public void collision(Organism attacker) {
        if (this.getClass() == attacker.getClass()) {
            breed(attacker);
        } else {
            if (attacker.strength >= this.strength) {
                if (this instanceof Plant) world.updateDescription(attacker + " ate " + this);
                else world.updateDescription(attacker + " killed " + this);
                die();
                attacker.location = location;
            }
            else {
                if (this instanceof Plant) {
                    world.updateDescription(attacker + " died after eating " + this);
                    die();
                }
                else world.updateDescription(attacker + " died while attacking " + this);
                attacker.die();
            }
        }
    }

    public void age() {
        age++;
    }
    public void die() {
        for (int i = 0; i < world.organisms.size(); i++) {
            if (world.organisms.get(i).equals(this)) {
                world.organisms.remove(i);
                break;
            }
        }
        world.map[location.getY()][location.getX()] = null;
    }

    protected void breed(Organism partner) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Point direction = new Point(location.getX() + j, location.getY() + i);
                if (!exceedsMap(direction) && isEmpty(direction)) {
                    int id;
                    if (partner instanceof Antelope) id = 1;
                    else if (partner instanceof Fox) id = 2;
                    else if (partner instanceof Sheep) id = 3;
                    else if (partner instanceof Tortoise) id = 4;
                    else if (partner instanceof Wolf) id = 5;
                    else if (partner instanceof Belladonna) id = 6;
                    else if (partner instanceof Dandelion) id = 7;
                    else if (partner instanceof Grass) id = 8;
                    else if (partner instanceof Guarana) id = 9;
                    else id = 10;
                    world.addOrganism(id, direction);
                    if (partner instanceof Plant) world.updateDescription(this + " spread to a nearby field");
                    else world.updateDescription(partner + " made child with " + this);
                    return;
                }
            }
        }
        if (this instanceof Animal) isStuck();
    }

    protected boolean exceedsMap(Point location) {
        return location.getX() < 0 || location.getX() >= world.getWidth()
                || location.getY() < 0 || location.getY() >= world.getHeight();
    }

    protected boolean isEmpty(Point location) {
        return world.map[location.getY()][location.getX()] == null;
    }

    protected void isStuck() {
        world.updateDescription(this + " could not move");
    }

    public abstract Color toColor();

    boolean equals(Organism other) {
        if (other == null) return false;
        else return this.toString().equals(other.toString()) && this.age == other.age;//&& location.getX() == other.location.getX() && location.getY() == other.location.getY();
    }
}
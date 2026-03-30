package simulator.organisms.animals;

import simulator.organisms.Colors;
import simulator.organisms.Organism;
import tools.Point;
import simulator.World;
import tools.RandomNumberGenerator;

import java.awt.*;

public class Tortoise extends Animal implements java.io.Serializable {
    public Tortoise(World world, Point location) {
        super(2, 1, world, location);
    }

    @Override
    public void action() {
        if (RandomNumberGenerator.getInt(1, 4) < 4) {
            move(1, false);
        } else world.updateDescription(this + " did not move");
    }

    @Override
    public void collision(Organism attacker) {
        if (this.getClass() == attacker.getClass() || attacker.getStrength() >= 5) super.collision(attacker);
        else world.updateDescription(attacker + " bounced of a " + this);
    }

    @Override
    public Color toColor() {
        return Colors.TORTOISE.get();
    }

    @Override
    public String toString() {
        return "Tortoise (" + strength + ") " + location;
    }
}

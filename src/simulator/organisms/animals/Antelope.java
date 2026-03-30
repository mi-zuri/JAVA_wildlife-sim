package simulator.organisms.animals;

import simulator.organisms.Colors;
import tools.Point;
import simulator.World;
import simulator.organisms.Organism;
import tools.RandomNumberGenerator;

import java.awt.*;

public class Antelope extends Animal implements java.io.Serializable {
    public Antelope(World world, Point location) {
        super(4, 4, world, location);
    }

    @Override
    public void action() {
        move(2, false);
    }

    @Override
    public void collision(Organism attacker) {
        if (this.getClass() == attacker.getClass() || RandomNumberGenerator.getInt(1, 2) == 2) {
            // didn't manage to flee
            super.collision(attacker);
        } else if (!flee(attacker)) {
            // didn't manage to move
            super.collision(attacker);
        }
    }

    @Override
    public Color toColor() {
        return Colors.ANTELOPE.get();
    }

    @Override
    public String toString() {
        return "Antelope (" + strength + ") " + location;
    }
}
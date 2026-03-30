package simulator.organisms.plants;

import simulator.organisms.Colors;
import tools.Point;
import simulator.World;
import tools.RandomNumberGenerator;

import java.awt.*;
import java.io.Serial;

public class Dandelion extends Plant implements java.io.Serializable {

    public Dandelion(World world, Point location) {
        super(world, location);
    }

    @Override
    public void action() {
        for (int i = 0; i < 3; i++) {
            if (RandomNumberGenerator.getInt(1, 10) == 1) {
                breed(this);
            }
        }
    }

    @Override
    public Color toColor() {
        return Colors.DANDELION.get();
    }

    @Override
    public String toString() {
        return "Dandelion " + location;
    }
}
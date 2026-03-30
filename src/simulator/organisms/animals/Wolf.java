package simulator.organisms.animals;

import simulator.organisms.Colors;
import tools.Point;
import simulator.World;

import java.awt.*;

public class Wolf extends Animal implements java.io.Serializable {
    public Wolf(World world, Point location) {
        super(9, 5, world, location);
    }

    @Override
    public Color toColor() {
        return Colors.WOLF.get();
    }

    @Override
    public String toString() {
        return "Wolf (" + strength + ") " + location;
    }
}
package simulator.organisms.animals;

import simulator.organisms.Colors;
import tools.Point;
import simulator.World;

import java.awt.*;

public class Sheep extends Animal implements java.io.Serializable {
    public Sheep(World world, Point location) {
        super(4, 4, world, location);
    }

    @Override
    public Color toColor() {
        return Colors.SHEEP.get();
    }

    @Override
    public String toString() {
        return "Sheep (" + strength + ") " + location;
    }
}

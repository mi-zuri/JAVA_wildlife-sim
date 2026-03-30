package simulator.organisms.plants;

import simulator.organisms.Colors;
import tools.Point;
import simulator.World;

import java.awt.*;
import java.io.Serial;

public class Grass extends Plant implements java.io.Serializable {

    public Grass(World world, Point location) {
        super(world, location);
    }

    @Override
    public Color toColor() {
        return Colors.GRASS.get();
    }

    @Override
    public String toString() {
        return "Grass " + location;
    }
}
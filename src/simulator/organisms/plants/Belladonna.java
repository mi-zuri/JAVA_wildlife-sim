package simulator.organisms.plants;

import simulator.organisms.Colors;
import tools.Point;
import simulator.World;

import java.awt.*;
import java.io.Serial;

public class Belladonna extends Plant implements java.io.Serializable {
    public Belladonna(World world, Point location) {
        super(99, world, location);
    }

    @Override
    public Color toColor() {
        return Colors.BELLADONNA.get();
    }

    @Override
    public String toString() {
        return "Belladonna " + location;
    }
}
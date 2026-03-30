package simulator.organisms.animals;

import simulator.organisms.Colors;
import tools.Point;
import simulator.World;

import java.awt.*;

public class Fox extends Animal implements java.io.Serializable {
    public Fox(World world, Point location) {
        super(3, 7, world, location);
    }

    @Override
    public void action() {
        move(1, true);
    }

    @Override
    public Color toColor() {
        return Colors.FOX.get();
    }

    @Override
    public String toString() {
        return "Fox (" + strength + ") " + location;
    }
}

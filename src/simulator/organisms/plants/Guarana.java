package simulator.organisms.plants;

import simulator.organisms.Colors;
import simulator.organisms.Organism;
import tools.Point;
import simulator.World;

import java.awt.*;

public class Guarana extends Plant implements java.io.Serializable {
    public Guarana(World world, Point location) {
        super(world, location);
    }

    @Override
    public void collision(Organism attacker) {
        super.collision(attacker);
        attacker.setStrength(attacker.getStrength() + 3);
    }

    @Override
    public Color toColor() {
        return Colors.GUARANA.get();
    }

    @Override
    public String toString() {
        return "Guarana " + location;
    }
}
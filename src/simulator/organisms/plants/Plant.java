package simulator.organisms.plants;

import tools.Point;
import simulator.World;
import simulator.organisms.Organism;
import tools.RandomNumberGenerator;

public abstract class Plant extends Organism implements java.io.Serializable {
    public Plant(World world, Point location) {
        super(0, 0, world, location);
    }
    public Plant(int strength, World world, Point location) {
        super(strength, 0, world, location);
    }

    @Override
    public void action() {
        if (RandomNumberGenerator.getInt(1, 10) == 1) {
            breed(this);
        }
    }
}
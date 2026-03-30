package simulator.organisms.plants;

import simulator.organisms.Colors;
import tools.Point;
import simulator.World;
import tools.RandomNumberGenerator;

import java.awt.*;
import java.io.Serial;

public class SosnowskysHogweed extends Plant implements java.io.Serializable {
    public SosnowskysHogweed(World world, Point location) {
        super(10, world, location);
    }

    @Override
    public void action() {
        if (RandomNumberGenerator.getInt(1, 3) == 1) {
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue;
                    Point direction = new Point(location.getX() + j, location.getY() + i);
                    if (!exceedsMap(direction) && !isEmpty(direction)) {
                        world.map[direction.getY()][direction.getX()].die();
                    }
                    world.updateDescription(this + " obliterated everything nearby");
                }
            }
        }
    }

    @Override
    public Color toColor() {
        return Colors.SOSNOWSKYS_HOGWEED.get();
    }

    @Override
    public String toString() {
        return "Sosnowsky's Hogweed " + location;
    }
}
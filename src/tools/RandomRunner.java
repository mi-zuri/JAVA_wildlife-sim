package tools;

import simulator.World;

import java.util.ArrayList;
import java.util.List;

public class RandomRunner implements java.io.Serializable {
    List<Runnable<World, Point>> optionPool = new ArrayList<>();

    // adds a method weight number of times to the pool
    public void add(Runnable<World, Point> toRun, int weight) {
        for (int i = 0; i < weight; i++) {
            optionPool.add(toRun);
        }
    }

    // runs random method from the pool
    public void run(World world, Point location) {
        if (!optionPool.isEmpty())
            optionPool.get(RandomNumberGenerator.getInt(0, optionPool.size() - 1)).run(world, location);
    }
}

// Source: https://stackoverflow.com/questions/45836397/coding-pattern-for-random-percentage-branching
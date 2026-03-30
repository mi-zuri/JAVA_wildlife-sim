package simulator.organisms.animals;

import simulator.organisms.Colors;
import tools.Point;
import simulator.World;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Human extends Animal implements java.io.Serializable {
    public Human(World world, Point location) {
        super(5, 4, world, location);
    }

    @Override
    public void action() {
        world.updateDescription("it's your time to move " + this);
        world.waitingForInput = true;
    }

    @Override
    public Color toColor() {
        return Colors.HUMAN.get();
    }

    @Override
    public String toString() {
        return "Human (" + strength + ") " + location;
    }
}

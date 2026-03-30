package gui;

import simulator.World;
import simulator.organisms.Organism;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tools.Point;

public class Field extends JButton implements ActionListener {
    GUI gui;
    Organism organism;
    World world;
    private final int x;
    private final int y;
    public Field(GUI gui, World world, Point location) {
        this.gui = gui;
        this.world = world;
        this.x = location.getX();
        this.y = location.getY();
        this.setSize(20, 20);
        this.setOpaque(true);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
        updateColor();
        this.addActionListener(this);
    }

    public void updateColor() {
        organism = world.map[y][x];
        if (organism == null) this.setBackground(Color.BLACK);
        else this.setBackground(organism.toColor());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (organism == null) {
            world.organismCreator.run(world, new Point(x, y));
            world.updateMap();
            gui.updateGUI();
        }
    }
}

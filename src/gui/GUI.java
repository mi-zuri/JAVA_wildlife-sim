package gui;

import simulator.Simulator;
import simulator.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GUI extends JFrame implements ActionListener {
    Simulator simulator;
    World world;
    Field[][] fields;
    JButton nextDay;
    JButton next;
    JButton save;
    JButton load;
    JTextField[] info;
    JTextField day;

    public GUI(Simulator simulator, World world) {
        this.simulator = simulator;
        world.waitingForInput = false;
        this.world = world;
        this.setTitle("Wildlife Simulator");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize((20*20 + 20)*2, 20*20 + 20 + 30 + 50); // 30???
        this.setResizable(false);
        this.setLocationRelativeTo(null); // centers the frame

//        JPanel worldVisualization = new JPanel();
//        worldVisualization.setLayout(new GridLayout(world.getHeight(), world.getWidth()));
        assignFields();
//        worldVisualization.setLocation(10, 10);
//        this.add(worldVisualization);

        nextDay = new JButton("skip");
        nextDay.setBounds(110, world.getHeight()*20 + 20, 140, 40);
        nextDay.addActionListener(this);
        this.add(nextDay);

        next = new JButton("next");
        next.setBounds(260, world.getHeight()*20 + 20, 140, 40);
        next.addActionListener(this);
        this.add(next);

        save = new JButton("save");
        save.setBounds(530, world.getHeight()*20 + 20, 140, 40);
        save.addActionListener(this);
        this.add(save);

        load = new JButton("load");
        load.setBounds(680, world.getHeight()*20 + 20, 140, 40);
        load.addActionListener(this);
        this.add(load);

        info = new JTextField[20];
        for (int i = 0; i < info.length; i++) {
            info[i] = new JTextField();
            if (i == 0) info[i].setText("Michał Żurawski 193287");
            info[i].setBounds(435, 10 + i*20, 400, 20);
            this.add(info[i]);
        }

        day = new JTextField("Day: " + world.getDay());
        day.setBounds(25, world.getHeight()*20 + 30, 70, 20);
        this.add(day);

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new MyDispatcher());

        this.setVisible(true);
        this.getContentPane().setBackground(new Color(0x123456));
    }

    private void assignFields() {
        fields = new Field[world.getHeight()][world.getWidth()];
        for (int i = 0; i < world.getHeight(); i++) {
            for (int j = 0; j < world.getWidth(); j++) {
                fields[i][j] = new Field(this, world, new tools.Point(j, i));
                fields[i][j].setLocation(10 + j*20, 10 + i*20);
                this.add(fields[i][j]);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save) {
            simulator.save();
        }
        else if (e.getSource() == load) {
            simulator.load();
        }
        else if (!world.waitingForInput) {
            if (e.getSource() == nextDay){
                world.nextDay();
            }
            else if (e.getSource() == next) {
                world.nextAction();
            }
            updateGUI();
        }
    }

    public void updateGUI() {
        for (int i = 0; i < world.getHeight(); i++) {
            for (int j = 0; j < world.getWidth(); j++) {
                fields[i][j].updateColor();
            }
        }
        for (int i = world.description.size() - 1; i >= 0; i--) {
            info[info.length - world.description.size() + i].setText(world.description.get(i));
        }
        day.setText("Day: " + world.getDay());
    }

    private class MyDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (world.waitingForInput && e.getID() == KeyEvent.KEY_PRESSED) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_SPACE -> world.humanAbility();
                    case KeyEvent.VK_UP -> {
                        world.moveHuman(1);
                        updateGUI();
                        world.waitingForInput = false;
                    }
                    case KeyEvent.VK_DOWN -> {
                        world.moveHuman(2);
                        updateGUI();
                        world.waitingForInput = false;
                    }
                    case KeyEvent.VK_LEFT -> {
                        world.moveHuman(3);
                        updateGUI();
                        world.waitingForInput = false;
                    }
                    case KeyEvent.VK_RIGHT -> {
                        world.moveHuman(4);
                        updateGUI();
                        world.waitingForInput = false;
                    }
                }
            }
            return false;
        }
    }
}

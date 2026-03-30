package simulator;

import simulator.organisms.Organism;
import simulator.organisms.animals.*;
import simulator.organisms.plants.*;
import tools.Point;
import tools.RandomNumberGenerator;
import tools.RandomRunner;

import java.util.*;

public class World implements java.io.Serializable {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final int CHANCE_OF_CREATION = 100; // in percentage

    private static final int SHEEP_OF = 3;
    private static final int FOX_OF = 3;
    private static final int WOLF_OF = 2;
    private static final int ANTELOPE_OF = 3;
    private static final int TORTOISE_OF = 5;
    private static final int GRASS_OF = 50;
    private static final int DANDELION_OF = 17;
    private static final int GUARANA_OF = 10;
    private static final int BELLADONNA_OF = 4;
    private static final int SOSNOWSKYS_HOGWEED_OF = 3;

    public RandomRunner organismCreator;
    public List<Organism> organisms;
    private Queue<Organism> actionOrder;
    public Organism[][] map;
    public List<String> description;
    public boolean waitingForInput;
    private int day;
    private int counter = -5;
    public int getDay() {
        return day;
    }

    public int getHeight() {
        return map.length;
    }

    public int getWidth() {
        return map[0].length;
    }

    public World() {
        day = 1;
        initOrganismCreator();
        initOrganismsList();
        initMap();
        description = new ArrayList<>();
    }

    private void initOrganismCreator() {
        // creates wildlife pool
        organismCreator = new RandomRunner();
        // animals
        organismCreator.add((world, point) -> {
            organisms.add(new Sheep(world, point));
        }, SHEEP_OF);
        organismCreator.add((world, point) -> {
            organisms.add(new Fox(world, point));
        }, FOX_OF);
        organismCreator.add((world, point) -> {
            organisms.add(new Wolf(world, point));
        }, WOLF_OF);
        organismCreator.add((world, point) -> {
            organisms.add(new Antelope(world, point));
        }, ANTELOPE_OF);
        organismCreator.add((world, point) -> {
            organisms.add(new Tortoise(world, point));
        }, TORTOISE_OF);
        // plants
        organismCreator.add((world, point) -> {
            organisms.add(new Grass(world, point));
        }, GRASS_OF);
        organismCreator.add((world, point) -> {
            organisms.add(new Dandelion(world, point));
        }, DANDELION_OF);
        organismCreator.add((world, point) -> {
            organisms.add(new Guarana(world, point));
        }, GUARANA_OF);
        organismCreator.add((world, point) -> {
            organisms.add(new Belladonna(world, point));
        }, BELLADONNA_OF);
        organismCreator.add((world, point) -> {
            organisms.add(new SosnowskysHogweed(world, point));
        }, SOSNOWSKYS_HOGWEED_OF);
    }

    private void initOrganismsList() {
        organisms = new ArrayList<>();
        Point humanLoc = new Point(RandomNumberGenerator.getInt(0, WIDTH - 1), RandomNumberGenerator.getInt(0, HEIGHT - 1));
        // adds human
        organisms.add(new Human(this, humanLoc));
        // adds wildlife
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                // draws whether to create an organism
                if ((RandomNumberGenerator.getInt(1, 100) <= CHANCE_OF_CREATION)
                        // makes sure it's not next to the human
                        && (!((i >= humanLoc.getY() - 1 && i <= humanLoc.getY() + 1)
                        && (j >= humanLoc.getX() - 1 && j <= humanLoc.getX() + 1)))) {
                    organismCreator.run(this, new Point(j, i));
                }
            }
        }
    }

    public void addOrganism(int organismID, Point location) {
        switch (organismID) {
            case 1 -> organisms.add(new Antelope(this, location));
            case 2 -> organisms.add(new Fox(this, location));
            case 3 -> organisms.add(new Sheep(this, location));
            case 4 -> organisms.add(new Tortoise(this, location));
            case 5 -> organisms.add(new Wolf(this, location));
            case 6 -> organisms.add(new Belladonna(this, location));
            case 7 -> organisms.add(new Dandelion(this, location));
            case 8 -> organisms.add(new Grass(this, location));
            case 9 -> organisms.add(new Guarana(this, location));
            case 10 -> organisms.add(new SosnowskysHogweed(this, location));
        }
        updateMap();
    }

    private void initMap() {
        // assigns organisms to map
        map = new Organism[HEIGHT][WIDTH];
        updateMap();
    }

    public void updateMap() {
        // clears the map
        for (Organism[] field : map) {
            Arrays.fill(field, null);
        }
        // assigns organisms to the map
        organisms.forEach(organism -> {
            map[organism.getLocation().getY()][organism.getLocation().getX()] = organism;
        });
    }

    public void initQueue() {
        // sorts organisms based on initiative and age
        organisms.sort((o1, o2) -> {
            if (o1.getInitiative() == o2.getInitiative()) return o2.getAge() - o1.getAge();
            else return o2.getInitiative() - o1.getInitiative();
        });

        // assigns organism to the queue
        actionOrder = new LinkedList<>(organisms);
    }

    public void nextAction() {
        // init queue if empty
        if (actionOrder == null || actionOrder.peek() == null) initQueue();
        // run first action in the queue
        Organism temp = actionOrder.peek();
        if (temp != null) {
            if (temp.equals(map[temp.getLocation().getY()][temp.getLocation().getX()])) {
                // plants' actions are run all at once
                if (temp instanceof Plant) {
                    while (actionOrder.peek() != null) {
                        actionOrder.peek().age();
                        actionOrder.poll().action();
                    }
                } else {
                    temp.age();
                    if (temp instanceof Human) temp.action();
                    else actionOrder.poll().action();
                }
            }
            else actionOrder.remove();
        }
        if (actionOrder.peek() == null) day++;
        // update map state
        updateMap();
    }

    public void nextDay() {
        // init queue if empty
        if (actionOrder == null || actionOrder.peek() == null) initQueue();
        while (actionOrder.peek() != null) {
            if (actionOrder.peek() instanceof Human) {
                nextAction();
                break;
            } else nextAction();
        }

    }

    public void updateDescription(String text) {
        if (description.size() == 20) description.remove(0);
        description.add(text);
    }

    public void humanAbility() {
        if (counter <= -5) {
            counter = 5;
            updateDescription("Human used its special ability");
        }
    }

    public void moveHuman(int direction) {
        Human human = (Human) actionOrder.poll();
        int distance = 1;
        if (counter > 2 || (counter > 0 && RandomNumberGenerator.getInt(1, 2) == 2)) distance = 2;
        switch (direction) {
            case 1 -> human.up(distance, false, 1);
            case 2 -> human.down(distance, false, 1);
            case 3 -> human.left(distance, false, 1);
            case 4 -> human.right(distance, false, 1);
        }
        updateMap();
        if (counter == 1) updateDescription("Special ability not longer active");
        counter--;
    }
}
package simulator.organisms.animals;

import tools.Point;
import simulator.World;
import simulator.organisms.Organism;
import tools.RandomNumberGenerator;

public abstract class Animal extends Organism {
    public Animal(int strength, int initiative, World world, Point location) {
        super(strength, initiative, world, location);
    }

    @Override
    public void action() {
        move(1, false);
    }

    protected void move(int distance, boolean safe) {
        switch (RandomNumberGenerator.getInt(1, 4)) {
            case 1 -> up(distance, safe, 1);
            case 2 -> down(distance, safe, 1);
            case 3 -> left(distance, safe, 1);
            case 4 -> right(distance, safe, 1);
        }
    }

    private void moveDifferently(int distance, boolean safe, int tryCount) {
        switch (tryCount) {
            case 1 -> up(distance, safe, tryCount + 1);
            case 2 -> down(distance, safe, tryCount + 1);
            case 3 -> left(distance, safe, tryCount + 1);
            case 4 -> right(distance, safe, tryCount + 1);
            default -> isStuck();
        }
    }

    public void up(int distance, boolean safe, int tryCount) {
        Point direction = new Point(location.getX(), location.getY() - distance);
        if (!exceedsMap(direction)) {
            if (isEmpty(direction)) {
                location.setY(direction.getY()); // move
                world.updateDescription(this + " moved up");
                return;
            }
            else if (!safe || isSafe(direction)) {
                world.map[direction.getY()][direction.getX()].collision(this); // attack
                return;
            }
        }
        moveDifferently(distance, safe, tryCount);
    }
    public void down(int distance, boolean safe, int tryCount) {
        Point direction = new Point(location.getX(), location.getY() + distance);
        if (!exceedsMap(direction)) {
            if (isEmpty(direction)) {
                location.setY(direction.getY()); // move
                world.updateDescription(this + " moved down");
                return;
            }
            else if (!safe || isSafe(direction)) {
                world.map[direction.getY()][direction.getX()].collision(this); // attack
                return;
            }
        }
        moveDifferently(distance, safe, tryCount);
    }
    public void left(int distance, boolean safe, int tryCount) {
        Point direction = new Point(location.getX() - distance, location.getY());
        if (!exceedsMap(direction)) {
            if (isEmpty(direction)) {
                location.setX(direction.getX()); // move
                world.updateDescription(this + " moved left");
                return;
            }
            else if (!safe || isSafe(direction)) {
                world.map[direction.getY()][direction.getX()].collision(this); // attack
                return;
            }
        }
        moveDifferently(distance, safe, tryCount);
    }
    public void right(int distance, boolean safe, int tryCount) {
        Point direction = new Point(location.getX() + distance, location.getY());
        if (!exceedsMap(direction)) {
            if (isEmpty(direction)) {
                location.setX(direction.getX()); // move
                world.updateDescription(this + " moved right");
                return;
            }
            else if (!safe || isSafe(direction)) {
                world.map[direction.getY()][direction.getX()].collision(this); // attack
                return;
            }
        }
        moveDifferently(distance, safe, tryCount);
    }



    protected boolean flee(Organism attacker) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Point direction = new Point(location.getX() + j, location.getY() + i);
                if (!exceedsMap(direction) && isEmpty(direction)) {
                    attacker.setLocation(location);
                    location = direction;
                    world.updateDescription(this + " fled from " + attacker);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isSafe(Point location) {
        return world.map[location.getY()][location.getX()].getStrength() <= this.strength;
    }


}
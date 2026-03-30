package simulator.organisms;

import java.awt.*;

public enum Colors {
    HUMAN(90, 10, 169),
    ANTELOPE(212, 135, 80),
    FOX(130, 60, 10),
    SHEEP(177, 177, 177),
    TORTOISE(49, 69, 39),
    WOLF(35, 35, 35),
    BELLADONNA(6, 10, 140),
    DANDELION(155, 130, 10),
    GRASS(9, 99, 9),
    GUARANA(130, 8, 65),
    SOSNOWSKYS_HOGWEED(65, 45, 10);

    private final Color color;

    Colors(int r, int g, int b) {
        color = new Color(r, g, b);
    }

    public Color get() {
        return color;
    }
}

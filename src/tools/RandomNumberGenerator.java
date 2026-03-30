package tools;

import java.util.concurrent.ThreadLocalRandom;

public class RandomNumberGenerator {
    public static int getInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}

package testing;

import java.util.Random;

public class FixedRandom extends Random {

    @Override
    public int nextInt(int bound) {
        return 0;
    }
}

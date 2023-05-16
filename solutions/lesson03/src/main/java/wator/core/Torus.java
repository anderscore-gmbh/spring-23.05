package wator.core;


import org.springframework.lang.Nullable;

/**
 * This interface specifies the contract a Torus has to fulfill.
 */
public interface Torus {

    /**
     * @return the fish at the given coordinates or null if there is none. The coordinates will be normalized.
     */
    @Nullable Fish getFishAt(int x, int y);
    void setFishAt(int x, int y, @Nullable Fish fish);

    int getWidth();
    int getHeight();

    /**
     * This method realizes the torus property that is if a v &lt; 0 or v &gt;= max
     * the v continues at the other end. The methods {@link #getFishAt(int, int)}
     * and {@link #setFishAt(int, int, Fish)} must use this method
     * to normalize to coordinates x and y.
     *
     * @param v the coordinate value to normalize
     * @param max width or height of the torus
     * @return w = v + n * max so that 0 &lt; w &lt;= max
     */
    default int normalize(int v, int max) {
        v = v % max;
        if (v < 0) {
            v += max;
        }
        return v;
    }
}

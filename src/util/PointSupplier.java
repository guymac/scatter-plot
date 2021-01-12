package util;

import model.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashSet;
import java.util.Comparator;

/**
 * Class that generates random unique points
 * @author Kairi Kozuma
 * @version 2.0
 */
public class PointSupplier implements Supplier <Point> 
{
    private int X_MIN, X_MAX, Y_MIN, Y_MAX;

    AtomicInteger index = new AtomicInteger(0);

    /**
     * Generate list of random unique points from limits
     * @param   numberOfPoints number of points to generate
     * @param   X_MIN minimum x value
     * @param   X_MAX maximum x value
     * @param   Y_MIN minimum y value
     * @param   Y_MAX maximum y value
     * @return  list of unique Point objects
     */
    public PointSupplier(final int X_MIN, final int X_MAX, final int Y_MIN, final int Y_MAX) 
    {
        this.X_MIN = X_MIN;
        this.X_MAX = X_MAX;
        this.Y_MIN = Y_MIN;
        this.Y_MAX = Y_MAX;
    }


    @Override
    public Point get()
    {
        int deltaX = X_MAX - X_MIN;
        int deltaY = Y_MAX - Y_MIN;
        // Create random point within bounds
        int x = (int) Math.floor((deltaX + 1) * Math.random()) + X_MIN;
        int y = (int) Math.floor((deltaY + 1) * Math.random()) + Y_MIN;
        return new Point(x, y, index.getAndAdd(1));
    }


    public static List <Point> generate(int num, int[] bound)
    {
        if (bound == null || bound.length != 4 || num < 1) return Collections.emptyList();
        PointSupplier ps = new PointSupplier(bound[0], bound[1], bound[2], bound[3]);
        return Stream.generate(ps).distinct().limit(num).collect(Collectors.toList());
    }
}

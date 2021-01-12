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
    private Point upperLeft, lowerRight;

    /**
     * Supplier of of random points within limits
     * @param   upperLeft x_min, y_max;
     * @param   lowerRight x_max, y_min;
     */
    public PointSupplier(Point upperLeft, Point lowerRight) 
    {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;
    }


    @Override
    public Point get()
    {
        int deltaX = lowerRight.x() - upperLeft.x();
        int deltaY = upperLeft.y() - lowerRight.y();
        // Create random point within bounds
        int x = (int) Math.floor((deltaX + 1) * Math.random()) + upperLeft.x();
        int y = (int) Math.floor((deltaY + 1) * Math.random()) + lowerRight.y();
        return new Point(x, y);
    }

    /**
     * Generate a random list of unique (having distinct coordinates) points
     * 
     * @param num
     * @param bound
     * @return
     */
    public static List <Point> generate(int num, Point upperLeft, Point lowerRight)
    {
        if (upperLeft == null || lowerRight == null || num < 1) return Collections.emptyList();
        PointSupplier ps = new PointSupplier(upperLeft, lowerRight);
        return Stream.generate(ps).distinct().limit(num).collect(Collectors.toList());
    }
}

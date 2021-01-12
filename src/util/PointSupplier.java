package util;

import model.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Comparator;

/**
 * Class that generates random unique points
 * @author Kairi Kozuma
 * @version 1.0
 */
public class PointGenerator {

    /**
     * Generate list of random unique points from limits
     * @param   numberOfPoints number of points to generate
     * @param   X_MIN minimum x value
     * @param   X_MAX maximum x value
     * @param   Y_MIN minimum y value
     * @param   Y_MAX maximum y value
     * @return  list of unique Point objects
     */
    public static List<Point> generate(int numberOfPoints,
        final int X_MIN, final int X_MAX, final int Y_MIN, final int Y_MAX) {

        // Use set to avoid duplicate points
        Set<Point> mPoints = new HashSet<Point>();

        int deltaX = X_MAX - X_MIN;
        int deltaY = Y_MAX - Y_MIN;

        while (mPoints.size() < numberOfPoints) {

            // Create random point within bounds
            int x = (int) Math.floor((deltaX + 1) * Math.random()) + X_MIN;
            int y = (int) Math.floor((deltaY + 1) * Math.random()) + Y_MIN;

            mPoints.add(new Point(x, y, mPoints.size() + 1));
        }

        // Convert set to list
        List<Point> mPointList = new ArrayList<Point>(mPoints);

        // Sort by index
        mPointList.sort(Comparator.comparing(Point::getIndex));
        return mPointList;
    }

    /**
     * Generate list of random unique points from array of boundary
     * @param  numberOfPoints number of points to generate
     * @param  BOUND array of bounds [X_MIN, X_MAX, Y_MIN, Y_MAX]
     * @return list of unique Point objects
     */
    public static List<Point> generate(int numberOfPoints, final int[] BOUND) {
        return generate(numberOfPoints, BOUND[0], BOUND[1], BOUND[2], BOUND[3]);
    }
}

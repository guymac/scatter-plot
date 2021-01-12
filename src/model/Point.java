package model;

/**
 * Class that represents a single point in Cartesian coordinates
 * @author Kairi Kozuma
 * @version 2.0
 */
public record Point(int x, int y, int index) 
{

    public Point {}
    
    /**
     * Constructs point
     * @param   x x value
     * @param   y y value
     */
    public Point(int x, int y) 
    {
        this(x, y, 0);
    }

    /**
     * Constructs point
     * @param   x0 x value
     * @param   y0 y value
     * @param   i0 index value
     */
    public Point() 
    {
        this(0,0,0);
    }


}

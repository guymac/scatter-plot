package model;

/**
 * Class that represents a single point in Cartesian coordinates
 * @author Kairi Kozuma
 * @version 2.0
 */
public record Point(int x, int y) 
{
    public int getX()
    {
        return x();
    }
    
    public int getY()
    {
        return y();
    }
}

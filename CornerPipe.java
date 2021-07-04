/**
 * Single CornerPipe with two ends in an L shape.
 * Extends ProtoPipe for use in calculations
 *
 * Jebadiah Dudfield
 * 25/06/2021
 */
public class CornerPipe extends Pipe
{
    //Flow represents whether or not there is water currently flowing through the pipe,
    
    /**
     * Constructor for objects of class CornerPipe
     */
    public CornerPipe(int x, int y, int rotation)
    {
        //Same constructor jazz as the Pipe class, which this extends.
        super(x,y,rotation);
    }

    /**
     * Getter for the flowRate variable, for use in rendering and other pipes' calculations
     */
    public boolean getFlow()
    {
        // put your code here
        return super.getFlow();
    }
    
    /**
     * Integer direction represents the direction being checked; Starting at 0=north and proceeding clockwise. 
     * If direction is the same as rotation or rotation + 1 modulo 4, it is connected.
     * Rotation is equal to the direction of the anticlockwise connection, so rotation + 1 is the direction of the second connection
     */
    public boolean isConnected(int direction){
        return (direction%4 == rotation%4 || direction%4 == (rotation+1)%4);
    }
}

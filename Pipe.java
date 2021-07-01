/**
 * Single Pipe with two ends in opposite directions
 * Extends ProtoPipe for use in calculations
 *
 * Jebadiah Dudfield
 * 25/06/2021
 */
public class Pipe extends ProtoPipe
{
    //Flow represents whether or not there is water currently flowing through the pipe.
    private boolean flow;
    
    /**
     * Constructor for objects of class Pipe
     */
    public Pipe(int x, int y, int rotation)
    {
        //Pipes should be initialised with their locations for easier reference with algorithms later.
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }
    
    
    /**
     * Pipe's calculation is pretty simple. It checks the flow rate of the pipes its connected to, and gets the flowrate of the non-null one.
     * TODO: Add direction-checking and actual calculation code here.
     */
    public void calcFlowRate(boolean set){
        this.flow = set;
        System.out.println("testo" + flow);
    }

    /**
     * Getter for the flowRate variable, for use in rendering and other pipes' calculations
     */
    public boolean getFlow()
    {
        // put your code here
        return flow;
    }
    
    /**
     * Integer direction represents the direction being checked; Starting at 0=north and proceeding clockwise. 
     * Uses modulo 2 of both sides to simplify into up/down; a rotation of 0 or 2 means the pipe is vertical, so if direction is a multiple of 2 then its vertical as well.
     * This should hold for horizontal calculations the same way.
     */
    public boolean isConnected(int direction){
        return (direction%2 == rotation%2);
    }
}

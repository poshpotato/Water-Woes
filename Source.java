/**
 * Source, start of flow.
 * Extends ProtoPipe for use in calculations.
 *
 * Jebadiah Dudfield
 * 27/05/2021
 */
public class Source extends ProtoPipe
{
    //Flow represents whether or not there is water currently flowing through the pipe.
    //sources *always* have flow! they're sources!
    private final boolean flow = true;
    
    
    /**
     * Constructor for objects of class Source
     */
    public Source(int x, int y, int rotation)
    {
        //Pipes should be initialised with their locations for easier reference with algorithms later.
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }
    
    /**
     * Theres pretty much no reason to calculate flow rate with a source! It has a constant rate of On.
     */
    public void calcFlowRate(boolean flow){
        return;
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
     * Should just check direction against rotation and return that, as the rotation designates the side it is connected to in the same way as direction.
     */
    public boolean isConnected(int direction){
        return (direction == rotation);
    }
    
    public void setRotation(int newRot){
        this.rotation = newRot;
    }
}

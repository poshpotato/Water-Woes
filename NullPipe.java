/**
 * NullPipe is a placeholder pipe that stands in an empty cell.
 * Extends ProtoPipe for use in calculations.
 * Cannot be added manually, as it only represents an empty cell and is created through removal.
 *
 * Jebadiah Dudfield
 * 27/05/2021
 */
public class NullPipe extends ProtoPipe
{
    //Flow represents whether or not there is water currently flowing through the pipe.
    private boolean flow;
    //This int represents the rotation of the ProtoPipe, starting from a "default position" dependent on the ProtoPipe itself.
    //This does not matter for NullPipes.
    private int rotation;
    public int x;
    public int y;
    
    /**
     * Constructor for objects of class NullPipe
     */
    public NullPipe()
    {
        
    }
    
    /**
     * Doesn't need to calculate flow rate, get outta here. It doesnt exist.
     */
    public void calcFlowRate(ProtoPipe[][] parentGrid){
    
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
     * NullPipe instances are connected to nothing.
     */
    public boolean isConnected(int direction){
        return false;
    }
}

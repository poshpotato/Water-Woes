/**
 * XJunction; A four sided junction.
 * Extends ProtoPipe for use in calculations
 *
 * Jebadiah Dudfield
 * 27/05/2021
 */
public class XJunction extends Junction
{
    //Flow represents whether or not there is water currently flowing through the pipe.
    private boolean flow;
    //This int represents the rotation of the ProtoPipe, starting from a "default position" dependent on the ProtoPipe itself.
    //Rotations do not matter for XJunctions!
    private int rotation;
    public int x;
    public int y;
    
    /**
     * Constructor for objects of class XJunction
     */
    public XJunction(int x, int y, int rotation)
    {
        //Pipes should be initialised with their locations for easier reference with algorithms later.
        super(x,y,rotation);
    }
    
    /**
     * Calculates flow based upon the directions its connected to. Takes a parameter of the pipeNetwork it's in so it can get the pipes around it. 
     * A junction will be on if any of the pipes connected to it have flow.
     */
    public void calcFlowRate(ProtoPipe[][] parentGrid){
        ErrorReporter.reportError("XJunction Flow Not Implemented Yet");
    }

    /**
     * Getter for the flowRate variable, for use in rendering and other pipes' calculations.
     * TODO: add variable to track output flows, divide total flowrate by num of flows for the flow for a single output pipe.
     */
    public boolean getFlow()
    {
        // put your code here
        return flow;
    }
    
    /**
     * Integer direction represents the direction being checked; Starting at 0=north and proceeding clockwise. 
     * Its connected on every side just return true
     */
    public boolean isConnected(int direction){
        return (true);
    }
}

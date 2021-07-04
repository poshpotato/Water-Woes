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
    
    /**
     * Constructor for objects of class XJunction
     */
    public XJunction(int x, int y, int rotation)
    {
        //Pipes should be initialised with their locations for easier reference with algorithms later.
        super(x,y,rotation);
    }
    

    /**
     * Getter for the flowRate variable, for use in rendering and other pipes' calculations.
     * TODO: add variable to track output flows, divide total flowrate by num of flows for the flow for a single output pipe.
     */
    public boolean getFlow()
    {
        // put your code here
        return super.getFlow();
    }
    
    /**
     * Integer direction represents the direction being checked; Starting at 0=north and proceeding clockwise. 
     * Its connected on every side just return true
     */
    public boolean isConnected(int direction){
        return (true);
    }
}

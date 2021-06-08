/**
 * Junction with multiple ends
 * (yes, this is a pain to model. Yes, its the hardest part.)
 * Extends ProtoPipe for use in calculations
 *
 * Jebadiah Dudfield
 * 27/05/2021
 */
public class Junction extends ProtoPipe
{
    //Flowrate is private; use getFlowRate() to get it.
    private int flowRate;
    //the directions a pipe is attached to is stored as a boolean array of length 4.
    //Junctions can be connected to 3 or four directions. 
    //0: north 1: east 2: south 3: west
    private boolean[] directions;
    
    /**
     * Constructor for objects of class Junction
     */
    public Junction(int x, int y)
    {
        //Pipes should be initialised with their locations for easier reference with algorithms later.
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the flowRate variable, for use in rendering and other pipes' calculations.
     * TODO: add variable to track output flows, divide total flowrate by num of flows for the flow for a single output pipe.
     */
    public int getFlowRate()
    {
        // put your code here
        return flowRate;
    }
    
    /**
     * Takes no parameters and returns an boolean[] representing the directions the pipe is connected to. 
     */
    public boolean[] getDirections(){
        return directions;
    }
    
    /**
     * Used to set directions on placement.
     */
    public void setDirections(boolean[] dirsToSet){
        this.directions=dirsToSet;
    }
}

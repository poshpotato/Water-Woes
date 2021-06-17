/**
 * Source, start of flow.
 * Extends ProtoPipe for use in calculations.
 *
 * Jebadiah Dudfield
 * 27/05/2021
 */
public class Source extends ProtoPipe
{
    //Flowrate is isn't dynamic, calculation method should do nothing.
    private int flowRate = ProtoPipe.nullFlow;
    //the directions a pipe is attached to is stored as a boolean array of length 4.
    //Sources can be connected to any number of pipes or junctions.
    //0: north 1: east 2: south 3: west
    private boolean[] directions;
    
    
    /**
     * Constructor for objects of class Source
     */
    public Source(int x, int y)
    {
        //Pipes should be initialised with their locations for easier reference with algorithms later.
        this.x = x;
        this.y = y;
    }
    
    /**
     * Theres pretty much no reason to calculate flow rate with a source! It has a constant, manually set rate.
     */
    public void calcFlowRate(ProtoPipe[][] parentGrid){
    
    }

    /**
     * Getter for the flowRate variable, for use in rendering and other pipes' calculations
     */
    public int getFlowRate()
    {
        // put your code here
        return flowRate;
    }
    
    /**
     * Setter for the flowRate variable. Sources have the only manually assignable flowRate.
     */
    public void setFlowRate(int flowSet)
    {
        // put your code here
        this.flowRate = flowSet;
    }
    
    /**
     * Takes no parameters and returns an boolean[] representing the direction the pipe is connected to. 
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

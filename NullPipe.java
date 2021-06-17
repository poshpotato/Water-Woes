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
    //Flowrate should not be utilised.
    private int flowRate;
    //NullPipes should not be connected to anything.
    private boolean[] directions;
    
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
    public int getFlowRate()
    {
        // put your code here
        return flowRate;
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

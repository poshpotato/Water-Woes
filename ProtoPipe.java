/**
 * Ancestor class of all pipe types in the simulation.
 * 
 * Contains placeholders for:
 * Flow rate
 * Direction
 * Flow rate getter
 * 
 * @Jebadiah Dudfield
 * @20/05/2021
 */
public abstract class ProtoPipe
{
    //Flowrate is for private use as getter methods will be customised per pipe
    private int flowRate;
    //the directions a pipe is attached to is stored as a boolean array of length 4
    //0: north 1: east 2: south 3: west
    private boolean[] directions;
    
    //TODO: Add and implement calculation methods.
    
    //Abstract, no constructors.

    /**
     * Takes no parameters and returns an integer representing flowRate. Note: Implementations are expected to use the directions variable to check connected pipes' flow rate if neccessary.
     * A flow rate of -1 implies non-calculation.
     */
    public abstract int getFlowRate();
    
    
    /**
     * Takes no parameters and returns an boolean[] representing the direction the pipe is connected to. 
     */
    public abstract boolean[] getDirections();
    
    /**
     * Used to set directions on placement.
     */
    public abstract void setDirections(boolean[] dirsToSet);
}

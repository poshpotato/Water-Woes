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
    //flowRate is always positive, and a negative flowRate indicates an uncertain flow.
    private int flowRate;
    //the directions a pipe is attached to is stored as a boolean array of length 4
    //0: north 1: east 2: south 3: west
    private boolean[] directions;
    
    public int x;
    public int y;
    
    //The representative for nullFlow is negative 1, as flow rates should only ever be positive.
    public static int nullFlow = -1;
    
    //TODO: Add and implement calculation methods.
    //TODO: Implement constructors to take and set directions for each pipetype
    
    
    
    //Abstract, no constructors.

    /**
     * Calculates flow rate based upon the directions its connected to. Takes a parameter of the pipeNetwork it's in so it can get the pipes around it.
     */
    public abstract void calcFlowRate(ProtoPipe[][] parentGrid);
    
    
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

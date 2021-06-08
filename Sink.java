/**
 * Sink, essentialy nullfies flowrate
 * Extends ProtoPipe for use in calculations
 *
 * Jebadiah Dudfield
 * 27/05/2021
 */
public class Sink extends ProtoPipe
{
    //Flowrate of 0 as flowrate represents outwards flow. and it connects to nothing
    private int flowRate;
    //the directions a pipe is attached to is stored as a boolean array of length 4.
    //Sinks can be connected to any number of pipes or junctions.
    //0: north 1: east 2: south 3: west
    private boolean[] directions;
    
    
    /**
     * Constructor for objects of class Sink
     */
    public Sink(int x, int y)
    {
        //Pipes should be initialised with their locations for easier reference with algorithms later.
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for the flowRate variable, for use in rendering and other pipes' calculations
     */
    public int getFlowRate()
    {
        // why would you need this. this shouldn't be called!
        System.out.println("Someone wants to get water from a sink. This shouldn't be happening!");
        return 0;
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

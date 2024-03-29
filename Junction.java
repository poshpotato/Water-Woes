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
    //Flow represents whether or not there is water currently flowing through the pipe.
    private boolean flow;
    
    /**
     * Constructor for objects of class Junction
     */
    public Junction(int x, int y, int rotation)
    {
        //Pipes should be initialised with their locations for easier reference with algorithms later.
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }
    
    /**
     * Calculates flow based upon the directions its connected to. Takes a parameter of the pipeNetwork it's in so it can get the pipes around it. 
     * A junction will be on if any of the pipes connected to it have flow.
     */
    public void calcFlowRate(boolean set){
        this.flow = set;
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
     * For a junction, the rotation variable will represent the direction that isn't connected. Thus, it is connected if direction != rotation!
     */
    public boolean isConnected(int direction){
        return (direction != rotation);
    }
}

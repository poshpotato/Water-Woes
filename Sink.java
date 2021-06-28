/**
 * Sink, essentialy nullfies flowrate
 * Extends ProtoPipe for use in calculations
 *
 * Jebadiah Dudfield
 * 27/05/2021
 */
public class Sink extends ProtoPipe
{
    //Flow represents whether or not there is water currently flowing through the pipe.
    private boolean flow;
    
    /**
     * Constructor for objects of class Sink
     */
    public Sink(int x, int y, int rotation)
    {
        //Pipes should be initialised with their locations for easier reference with algorithms later.
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }
    
    /**
     * Calculates flow based upon the directions its connected to. Takes a parameter of the pipeNetwork it's in so it can get the pipes around it. 
     * Pipes should use rotation and isConnected to calculate.
     * A sink's flowrate is just the combined flowrate of the pipes connected to it.
     */
    public void calcFlowRate(boolean set){
        //Flowrate doesn't matter man
        return;
    }

    /**
     * Getter for the flow variable, for use in rendering and other pipes' calculations
     */
    public boolean getFlow()
    {
        // why would you need this. this shouldn't be called!
        return false;
    }
    
    /**
     * Integer direction represents the direction being checked; Starting at 0=north and proceeding clockwise. 
     * Should just check direction against rotation and return that, as the rotation designates the side it is connected to in the same way as direction.
     */
    public boolean isConnected(int direction){
        return (direction == rotation);
    }
}

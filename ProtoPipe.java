/**
 * Ancestor class of all pipe types in the simulation.
 * 
 * Contains placeholders for:
 * Flow rate
 * Direction
 * Flow rate getter
 * 
 * @Jebadiah Dudfield
 * @25/06/2021
 */
public abstract class ProtoPipe
{
    //flow represents whether or not there is water currently flowing through the pipe.
    private boolean flow;
    //This int represents the rotation of the ProtoPipe, starting from a "default position" dependent on the ProtoPipe itself.
    //As an example, the default position for a pipe will be 0, a vertical pipe. Every additional 1 on top of this rotates it 90 degrees.
    //Each ProtoPipe will implement its own code reliant upon this rotation.
    //For example, pipe uses rotation%2 to get its rotation as it only has two meaningful rotations.
    public int rotation;
    public int x;
    public int y;
    
    //TODO: Add and implement calculation methods.
    //Abstract, no constructors.

    /**
     * Calculates flow based upon the directions its connected to. Takes a parameter of the pipeNetwork it's in so it can get the pipes around it. 
     * Pipes should use rotation and isConnected to calculate whether or not its connected.
     * 
     * Note: despite the name of this method, this only sets flow rate.
     * Calculation is actually done in PipeNetwork
     */
    public abstract void calcFlowRate(boolean set);
    
    
    /**
     * Takes no parameters and returns an boolean representing flow.
     */
    public abstract boolean getFlow();
    
    
    /**
     * Integer direction represents the direction being checked; Starting at 0=north and proceeding clockwise. Each class will use its own method reliant its own shape or rotation.
     */
    public abstract boolean isConnected(int direction);
}

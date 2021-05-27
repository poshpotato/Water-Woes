
/**
 * PipeNetwork class represents the higher structure of the network of pipes.
 * 
 * Contains:
 * 2d Array representing the grid of pipes.
 * Control Methods for calculating flow
 *
 * @Jebadiah Dudfield
 * @27/05/2021
 */
public class PipeNetwork
{
    // pipeGrid stores ProtoPipes, which means all pipes that extend ProtoPipe can be stored and accessed in this array. Empty squares will be left null.
    public ProtoPipe[][] pipeGrid;
    
    /**
     * Constructor for objects of class PipeNetwork
     */
    public PipeNetwork()
    {
        
    }

    /**
     * Control method for updating flow. Should be called whenever the grid is adjusted.
     */
    public void determineFlowRates()
    {
        //To put it roughly, this algorithm starts from the sources and works along the pipes until it reaches the sinks.
        //It delays processing junctions until theres no normal pipes to process.
        
        //TODO: write code for this
    }
}

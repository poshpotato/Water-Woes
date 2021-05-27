
/**
 * PipeNetwork class represents the higher structure of the network of pipes.
 * 
 * Contains:
 * 2d Array representing the grid of pipes.
 * Methods for adding pipes to grid
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
    public PipeNetwork(int widthX, int heightY)
    {
        this.pipeGrid = new ProtoPipe[widthX][heightY];
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
    
    /**
     * Takes a String representing a type of pipe (canon type strings are "Pipe", "Junction", "Source", and "Sink"), and two integers representing the co-ordinates of the grid.
     * It uses these to construct a new pipe of that type and add it to the grid.
     */
    public void addPipe(String type, int x, int y){
        //TODO: write code for this. Use switch statement to clarify type
        //TODO: add checks for being valid x and y co-ords. use pipeGrid's dimensions for this.
    }
}

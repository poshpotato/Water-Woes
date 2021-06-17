/**
 * Single Pipe with two ends that can be in any direction.
 * Extends ProtoPipe for use in calculations
 *
 * Jebadiah Dudfield
 * 27/05/2021
 */
public class Pipe extends ProtoPipe
{
    //Flowrate is private; use getFlowRate() to get it. Initially set as whatever nullRep is defined as in ProtoPipe, and is reset to this at each recalculation.
    private int flowRate;
    //the directions a pipe is attached to is stored as a boolean array of length 4.
    //In practice, this should only be connected in two directions, and should be 
    //0: north 1: east 2: south 3: west
    private boolean[] directions;
    
    /**
     * Constructor for objects of class Pipe
     */
    public Pipe(int x, int y)
    {
        //Pipes should be initialised with their locations for easier reference with algorithms later.
        this.x = x;
        this.y = y;
    }
    
    
    /**
     * Pipe's calculation is pretty simple. It checks the flow rate of the pipes its connected to, and gets the flowrate of the non-null one.
     * TODO: Add direction-checking and actual calculation code here.
     */
    public void calcFlowRate(ProtoPipe[][] parentGrid){
        //This will track the flows added to the pipe; while there should only ever be coming.
        //Initialised with nullFlow so we can tell if its been set or not, we don't want to take from two different directions.
        int tempFlowTracker = -1;
        
        for(int i=0;i<directions.length;i++){
            //checks all directions, and runs code checking each direction that directions[] says its connected to.
            if(directions[i]){
                //Okay so its kinda convoluted but we
                
                //0. If the flowTracker is already set we return an error and exit the method by returning tempFlowTracker
                //There should only ever be one time that flowTracker is set for Pipe as it only has one input.
                if(tempFlowTracker != -1){
                    ErrorReporter.reportError("Pipe at " + x + "," + y + " has 2 inflow pipes; This shouldn't occur.");
                    flowRate = tempFlowTracker;
                    return;
                }
                
                //1. declare a variable for the pipe we're checking
                ProtoPipe adjacent = new NullPipe();
                
                //2. Use a switch statement to check direction and assign the correct pipe to adjacent.
                switch(i){
                    case 0:
                        //north
                        adjacent = parentGrid[x][y-1];
                        break;
                    case 1:
                        //east
                        adjacent = parentGrid[x+1][y];
                        break;
                    case 2:
                        //south
                        adjacent = parentGrid[x][y+1];
                        break;
                    case 3:
                        //west
                        adjacent = parentGrid[x-1][y];
                        break;
                    default:
                        ErrorReporter.reportError("Non-valid direction for calculating flow of pipe at " + x + "," + y + ": " + i 
                        + ", direction numbers should only go up to 4.");
                        break;
                }
                
                //3. Check if adjacent is a valid pipe to take flow from 
                //The criteria for taking flow from a pipe is:
                //The pipe doesn't have the nullFlow defined in ProtoPipe
                //The pipe isn't a NullPipe
                //The pipe does not already have a set flowrate (Pipes should take in one flow only) (This is checked earlier.)
                if(adjacent.getFlowRate() != ProtoPipe.nullFlow && adjacent.getClass().getName() != "NullPipe"){
                    //If these criteria are fulfilled, change the flow rate tracker.
                    tempFlowTracker = adjacent.getFlowRate();
                }
            }
        }
        
        //Once all of that is done, set the flowrate to the value stored.
        flowRate = tempFlowTracker;
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

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
     */
    public void calcFlowRate(){
        for(int i=0;i<directions.length;i++){
            //checks all directions, and runs code depending on which direction it is.
            if(directions[i]){
                switch(i){
                    case 0:
                        //north
                        
                }
            }
        }
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

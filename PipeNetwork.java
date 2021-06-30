




import java.util.ArrayList;
/**
 * PipeNetwork class represents the higher structure of the network of pipes.
 * 
 * Contains:
 * 2d Array representing the grid of pipes.
 * Methods for adding pipes to grid
 * Control Methods for calculating flow
 *
 * @Jebadiah Dudfield
 * @8/06/2021
 */
public class PipeNetwork
{
    // pipeGrid stores ProtoPipes, which means all pipes that extend ProtoPipe can be stored and accessed in this array. Empty squares will be left null.
    //pipeGrid is a twodimensional array, where the first represents the column, or x, and the second represents the row, or y.
    //IMPORTANT: all secondary arrays must be the same length. TODO: Make all flows reset before calculating.
    public ProtoPipe[][] pipeGrid;
    
    private ArrayList<Source> sourceList = new ArrayList<Source>();
    
    /**
     * Constructor for objects of class PipeNetwork.
     * Note, 
     */
    public PipeNetwork(int widthX, int heightY)
    {
        //No negative dimensions, and a upper limit of 100 spaces to prevent stack overflows.
        if(widthX<=0){
            ErrorReporter.reportError("Invalid width: " + widthX + ". Must be greater than 0, Defaulting to 10.");
            widthX=10;
        }else if (widthX>100){
            ErrorReporter.reportError("Width too large. Please limit sizes to 100 spaces or less to prevent crashes. Defaulting to 100.");
            widthX=100;
        }
        if(heightY <= 0){
            ErrorReporter.reportError("Invalid height: " + heightY + ". Must be greater than 0, Defaulting to 10.");
            heightY=10;
        }else if (heightY>100){
            ErrorReporter.reportError("Height too large. Please limit sizes to 100 spaces or less to prevent crashes. Defaulting to 100.");
            heightY=100;
        }
        //With checked dimensions, initialise the array and fill it with placeholder NullPipe objects.
        this.pipeGrid = new ProtoPipe[widthX][heightY];
        //And fill it with placeholder NullPipe objects
        for(int i=0;i<pipeGrid.length;i++){
            for(int j=0; j<pipeGrid[i].length; j++){
                pipeGrid[i][j] = new NullPipe();
            }
        }
    }

    /**
     * Control method for updating flow. Should be called whenever the grid is adjusted.
     */
    public void determineFlowRates()
    {
        //To put it roughly, this algorithm starts from the sources and works along the pipes until it reaches the sinks.
        ArrayList<ProtoPipe> toProcess = new ArrayList<ProtoPipe>();
        
        //First we get all the existing Source pipes
        for(int i = 0; i<sourceList.size();i++){
            toProcess.add(sourceList.get(i));
        }
        
        
        for(int i = 0; i<toProcess.size();i++){
            //This will run for every pipe connected to the sources
            //It should evaluate the flow of the pipe, and add adjacent pipes connected.
            ProtoPipe procPipe = toProcess.get(i);
            
            //Four seperate if statements for each direction that the pipe could be connected to. No, it's not pretty.
            //But hopefully, it should work.
            
            //it checks
            //1. Am I connected in this direction?
            //2. Is the pipe in this direction connected to me?
            
            //north
            if(procPipe.isConnected(0) && pipeGrid[procPipe.x][procPipe.y-1].isConnected(2)){
                toProcess.add(pipeGrid[procPipe.x][procPipe.y-1]);
            }
            
            //east
            if(procPipe.isConnected(1) && pipeGrid[procPipe.x+1][procPipe.y].isConnected(3)){
                toProcess.add(pipeGrid[procPipe.x+1][procPipe.y]);
            }
            
            //south
            if(procPipe.isConnected(2) && pipeGrid[procPipe.x][procPipe.y+1].isConnected(0)){
                toProcess.add(pipeGrid[procPipe.x][procPipe.y+1]);
            }
            
            //west
            if(procPipe.isConnected(3) && pipeGrid[procPipe.x-1][procPipe.y].isConnected(1)){
                toProcess.add(pipeGrid[procPipe.x-1][procPipe.y]);
            }
            
            //Finally mark this pipe as Full, cause if this is called it should be
            
            procPipe.calcFlowRate(true);
        }
    }
    
    /**
     * Takes a String representing a type of pipe (canon type strings are "Pipe", "CornerPipe", "Junction", "Source", and "Sink"), and two integers representing the co-ordinates of the grid.
     * It uses these to construct a new pipe of that type and add it to the grid.
     */
    public void addPipe(String type, int x, int y, int rotation){
        //This checks that the requested position is legitemate.
        if(x<0 || x >= pipeGrid.length || y < 0 || y >= pipeGrid[0].length){
            ErrorReporter.reportError("Invalid position to place pipe of type \"" + type + "\": position " + x + "," + y +". Must be between 0,0 and " + (pipeGrid.length-1) + "," + (pipeGrid[0].length-1));
            return;
        }
        
        //Switch statement for string. Could be changed to Integer parameter to save memory later, but thats a low priority TODO
        
        //Also, check if the position is already occupied. Formally remove the pipe that's already there if so.
        if(pipeGrid[x][y].getClass().getName() != "NullPipe"){
            this.removePipe(x,y);
        }
        
        //The given rotation is adjusted with modulo to fit to the parameters of each type of pipe; most of them need something between 0 and 3, but pipes only have two meaningful rotations.
        switch(type){
            case "Pipe":
                pipeGrid[x][y] = new Pipe(x,y,rotation%2);
                break;
            case "CornerPipe":
                pipeGrid[x][y] = new CornerPipe(x,y,rotation%4);
                break;
            case "Junction":
                pipeGrid[x][y] = new Junction(x,y,rotation%4);
                break;
            case "XJunction":
                //Rotation is irrelevant here, but we mod 4 it anyway.
                pipeGrid[x][y] = new XJunction(x,y,rotation%4);
                break;
            case "Source":
                //Sources are added to a seperate ArrayList for use in processing later.
                Source newSource = new Source(x,y,rotation%4);
                sourceList.add(newSource);
                pipeGrid[x][y] = newSource;
                break;
            case "Sink":
                pipeGrid[x][y] = new Sink(x,y,rotation%4);
                break;
            default:
                ErrorReporter.reportError("Invalid Pipe Type \"" + type + "\", Canon pipe types are \"Pipe\", \"Junction\", \"Source\", and \"Sink\"");
                break;
        }
        
    }
    
    public void removePipe(int x, int y){
        if(pipeGrid[x][y].getClass().getName() == "Source"){
            //if it is a source block, make sure to remove it from the sourceList ArrayList
            sourceList.remove(pipeGrid[x][y]);
            pipeGrid[x][y] = new NullPipe();
        }else{
            pipeGrid[x][y] = new NullPipe();
        }
    }
}

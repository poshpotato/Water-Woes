
/**
 * Quick and dirty debug/testing class for this program.
 *
 * @Jebadiah Dudfield
 * @15/06/2021
 */
public class DebugClass
{
    /**
     * Constructor for objects of class DebugClass
     */
    public DebugClass()
    {
    
    }

    /**
     * void static method that takes a PipeNetwork and prints out the names of all the spaces on it.
     *
     */
    public static void printNetwork(PipeNetwork toPrint)
    {
        ProtoPipe[][] grid = toPrint.pipeGrid;
        //prints the column numbers of the network.
        String headerLine = "   ";
        //The longest class name, Junction, is 8 chars long, so filler is added to shorter classes for consistent spacing.
        //This is to be changed if longer names are added. As of 25/06/2021, it is 10 with CornerPipe
        int squareLength = 10;
        for(int i=0;i<grid.length;i++){
            String chunk = Integer.toString(i);
            while(chunk.length() < 10){
                chunk += " ";
            }
            headerLine += chunk;
        }
        System.out.println(headerLine);
        for(int i=0;i<grid[1].length;i++){
            
            //Prints out the number of each row before each row.
            String line = Integer.toString(i);
            while(line.length() < 3){
                line+= " ";
            }
            for(int j=0; j<grid.length; j++){
                String className;
                //if it has a value, it gets the class name and adds spaces until it has a length of 8. If not, it just adds 8 spaces.
                if(grid[j][i]!=null){
                    Class pipeClass = grid[j][i].getClass();
                    className = pipeClass.getName();
                    while(className.length() < squareLength){
                        className+= " ";
                    }
                } else {
                    className = "        ";
                }
                line += className;
            }
            System.out.println(line);
        }
    }
    
    
}


/**
 * Quick and dirty debug/testing class for this program.
 *
 * @Jebadiah Dudfield
 * @8/06/2021
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
        //prints the height of the network
        System.out.println(grid.length);
        for(int i=0;i<grid.length;i++){
            //The longest class name, Junction, is 8 chars long, so filler is added to shorter classes for consistent spacing.
            int squareLength = 8;
            //Prints out the length of each row before each row.
            String line = Integer.toString(grid[i].length);
            while(line.length() < 3){
                line+= " ";
            }
            for(int j=0; j<grid[i].length; j++){
                String className;
                //if it has a value, it gets the class name and adds spaces until it has a length of 8. If not, it just adds 8 spaces.
                if(grid[i][j]!=null){
                    Class pipeClass = grid[i][j].getClass();
                    className = pipeClass.getName();
                    while(className.length() < 8){
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

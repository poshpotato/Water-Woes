
/**
 * Quick and dirty debug/testing class for this program.
 *
 * @Jebadiah Dudfield
 * @3/06/2021
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
    public static void sampleMethod(PipeNetwork toPrint)
    {
        ProtoPipe[][] grid = toPrint.pipeGrid;
        System.out.println(grid.length);
        for(int i=0;i<grid.length;i++){
            //The longest class name, Junction, is 8 chars long, so filler is added to shorter classes for consistent spacing.
            int squareLength = 8;
            String line = Integer.toString(grid[i].length);
            for(int j=0; j<grid[i].length; j++){
                String className = grid[i][j].getClass().getName();
                line += className;
            }
        }
    }
}

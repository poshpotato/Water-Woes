import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
/**
 * The WWWindow class is a Jframe used for the main GUI of the simulation.
 *
 * Responsible for:
 * Displaying menus
 *  Saving/Loading simulation states
 *  Selecting tools
 *  Quitting
 * Rendering simulation
 * Handling input
 * Controlling PipeNetwork
 * 
 * Basically, if its anything to do with simulation logic or handling user interactions, its in here.
 *
 * @Jebadiah Dudfield
 * @15/06/2021
 */
public class WWWindow extends JFrame implements ActionListener, MenuListener
{
    
    //Window stuff
    JMenuBar menuBar;
    JMenu file;
    JMenu pipes;
    JPanel panel;
    int xDimension = 600;
    int yDimension = 400;
    Canvas graphic;
    
    
    int xOffset = 8;
    int yOffset = 53;
    
    //Simulation Stuff
    PipeNetwork currentNetwork;
    
    //These are the dimensions of the pipe network that will be initialized.
    //In this version, its 8 by 8.
    int networkX = 8;
    int networkY = 8;
    
    JPanel pipePanel;
    
    //TODO List
    
    //Must do:
    //Make interactive menu
    //Make grid adjustable
    //Wire up calculations
    
    //Lower priority:
    //Implement saving and loading
    //Fix menu not repainting
    
    //If i have copious amounts of time left over:
    //Make cursor change when a pipe is selected.
    //Let the screen scale to 2 or 3 times size.
    
    
    /**
     * Constructor for objects of class WWWindow. TODO: Fill this with setup code.
     */
    public WWWindow()
    {
        if(!loadNetworkFromFile()){
            //This piece of code runs a function to attempt to load the previously worked upon network.
            //If that fails, it sets up a blank network.
            setUpNewNetwork();
        }
        setUpWindow();
        setUpMenus();
        renderWindow();
    }
    
    /**
     * Sets the basic size and title of the window
     */
    public void setUpWindow(){
       setTitle("Water-Woes 15/06/2021");
       
       
       this.getContentPane().setPreferredSize(new Dimension(xDimension,yDimension)); 
       this.setDefaultCloseOperation(EXIT_ON_CLOSE);
       
       //Boom. Penl time
       panel = new JPanel(new BorderLayout());
       //this is where the menu is, and is a third as wide as the window.
       panel.setPreferredSize(new Dimension(xDimension/3,yDimension));
       
       //I have had to do far too much hardcoding in my GUI code to let someone resize and fuck it all up!
       this.setResizable(false);
    
       Canvas graphic = new Canvas();
       panel.add(graphic);
    }
    
    /**
     * Packs the window and makes sure its visible.
     */
    public void renderWindow(){
        //re-set the menu to fit content and appear right
       this.pack();
       this.toFront();
       this.setVisible(true);
    }
    
    /**
     * Sets up the menu items.
     */
    public void setUpMenus(){
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        
        //File menu. Contains options to quit and create another network.
        
        file = new JMenu("File");
        //Note: this class implements MenuListener so it can handle repainting when menus are opened and closed.
        file.addMenuListener(this);
        menuBar.add(file);
        
        //Note: Savings hard!
        JMenuItem save = new JMenuItem("Save");
        save.addActionListener(this);
        file.add(save);
        JMenuItem newNetw = new JMenuItem("New");
        newNetw.addActionListener(this);
        file.add(newNetw);
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(this);
        file.add(quit);
        
        //Pipes menu. Changes selected Pipe.
        pipes = new JMenu("Pipes");
        pipes.addMenuListener(this);
        menuBar.add(pipes);
        JMenuItem pipe = new JMenuItem("Pipe");
        pipe.addActionListener(this);
        pipes.add(pipe);
        JMenuItem junction = new JMenuItem("Junction");
        junction.addActionListener(this);
        pipes.add(junction);
        JMenuItem source = new JMenuItem("Source");
        source.addActionListener(this);
        pipes.add(source);
        JMenuItem sink = new JMenuItem("Sink");
        sink.addActionListener(this);
        pipes.add(sink);
    }
    
    public void actionPerformed(ActionEvent e){
        String cmd = e.getActionCommand();
        //Switch statement to identify option selected.
        switch(cmd){
            case "Quit":
                System.exit(0);
                break;
            case "Save":
                ErrorReporter.reportError("Saving isn't currently implemented.");
                break;
            case "New":
                ErrorReporter.reportError("TODO: Wire up pipe system.");
                WWWindow l = new WWWindow();
                break;
            case "Red":
                this.getContentPane().setBackground(Color.RED);
                break;
            case "Blue":
                this.getContentPane().setBackground(Color.BLUE);
                break;
            case "Green":
                this.getContentPane().setBackground(Color.GREEN);
                break;
            default:
                ErrorReporter.reportError("Invalid Option \"" + cmd + "\".");
                break;
        }
        repaint();
    }
    
    
    
    /**
     * GUI hell begins below.
     */
    
    //THIS IS THE PAINT AND REPAINT SECTION. BEWARE ALL YE WHO ENTER HERE
    
    
    public void paint(Graphics g){
        //This infuriates me, frankly. I have to set all the sizes manually! MANUALLY! 
        //Its real frustrating, and I have to set the window to unresizable so taht people don't resize and goof it up!
        
        super.paint(g);
        
        //Boundary code. This should be a thin black line around the edge of the screen if the drawing area is 600 by 400.
        //The offsets are based on the menu height and some weird factor that means an x of 0 is 8 pixels to the left of the window.
        g.drawRect(xOffset,yOffset,599,400);
        drawGrid(g);
        drawPipeMenu(g);
        
        //The vague scheme of the 600 by 400 window is that the first 400 or so square pixels are dedicated to the grid. The right 200 by 400 pixels go to the menu.
    }
    
    
    
    /**
     * Here is where the grid and pipes are drawn. They take up a 400 by 400 area on the left of the window.
     */
    public void drawGrid(Graphics g){
        //In this 600 by 400 version of the simulation, the grid should be 8 by 8. Each 50x50 square consists of a 1px border and a 48x48 image of a pipe if there is a pipe in that spot there. 
        //This means the borders are 2px thick in total, from the neighbouring pipes.
        
        //This bit draws the grid lines. 
        //It also draws the pipes as of 28/06/2021
        for(int xCell=0; xCell<networkX; xCell++){
            //for each column
            for(int yCell = 0; yCell<networkY; yCell++){
                //for each row:
                //Draw an empty rectangle at the border of these bits.
                //functionally, xOffset and yOffset are at 0,0. So we add it to every objective coordinate used to draw.
                //Additionally, for each cell we check what cell it is and move 50px for each, as each is 50px wide/tall.
                g.drawRect(xOffset+(xCell*50),yOffset+(yCell*50),50,50);
                
                //Does a bunch of checks on the image to compile the filename
                //Pipe images are in /images/
                //Image names follow the format PipetypeStateRotation
                //For example, a flowing junction rotated 2 would be JunctionFull2
                
                //Get the pipe in this cell to process.
                ProtoPipe currentPipe = currentNetwork.pipeGrid[xCell][yCell];
                
                //This will be built into the filename.
                String fileName = "images/";
                
                switch(currentPipe.getClass().getName()){
                    case "Pipe":
                        fileName += "Pipe";
                        break;
                    case "CornerPipe":
                        fileName += "CornerPipe";
                        break;
                    case "Junction":
                        fileName += "Junction";
                        break;
                    case "XJunction":
                        fileName += "XJunction";
                        break;
                    case "Source":
                        fileName += "Source";
                        break;
                    case "Sink":
                        fileName += "Sink";
                        break;
                    case "NullPipe":
                        //If its null, it shouldn't be rendering an image here. 
                        //To prevent this breaking, the fileName is checked to see if it contains NullPipe in it 
                        //and just not render anything if so
                        fileName += "NullPipe";
                        break;
                    default:
                        ErrorReporter.reportError("Trying to render invalid pipetype at " + xCell + "," + yCell +".");
                }
                
                //then we check if its empty or not.
                if(currentPipe.getFlow()){
                    fileName+="Full";
                }else{
                    //if not flowing.
                    fileName += "Empty";
                }
                
                //Then we get rotation.
                fileName += Integer.toString(currentPipe.rotation);
                
                //Then add .png
                fileName += ".png";
                
                //at this point we should be left with fileName being the correct path to the image required.
                
                ImageIcon image = new ImageIcon(fileName);
                
                image.paintIcon(this,g,xOffset+(xCell*50)+1,yOffset+(yCell*50)+1);
            }
        }
        
    }
    
    /**
     * The pipe menu should take up the right side of the screen.
     * It consists of several clickable images of pipes in their rotations.
     * There are 6 sets of four rotations of pipes, or 24 in total.
     * If we split these into 6 columns of 4 50x50 pipe buttons
     * We have a total area of 150x400, which leaves us with 50 px of vertical space to seperate these columns
     * Thus, 10px between them.
     * This breaks down when we consider that Pipes and XJunctions only have 2 and 1 rotations respectfully.
     * Thus, the second row has only two columns. The third top column has only 3. 
     * For two columns, its 100 wide, so theres 100 free space. 
     * Ive split this into two 30px gaps to the sides and one 40px gap in the middle
     */
    public void drawPipeMenu(Graphics g){
        //we use seperate for loops, just to simplify some of the code-writing.
        
        //The x values are 10, 70, and 130,just to give 10px of space between the 50px images.
            
        //Source for loop:
        for(int i=0;i<4;i++){
            drawPipeButton(g,"Source",10,i*50,i);
        }
        
        //Sink for loop:
        for(int i=0;i<4;i++){
            drawPipeButton(g,"Sink",70,i*50,i);
        }
        
        //Pipe for loop:
        //(It's only two labels)
        for(int i=0;i<2;i++){
            drawPipeButton(g,"Pipe",130,i*50,i);
        }
        
        //XJunction button only has to be drawn once.
        drawPipeButton(g,"XJunction",130,100,0);
        
        //CornerPipe for loop:
        for(int i=0;i<4;i++){
            drawPipeButton(g,"CornerPipe",30,200+(i*50),i);
        }
        
        //Junction for loop:
        for(int i=0;i<4;i++){
            drawPipeButton(g,"Junction",120,200+(i*50),i);
        }
        
    }
    
    /**
     * Seperate method to draw a given button for ease of reading and writing.
     * x and y are the distances from the top left of the menu area.
     * Pipe Buttons should be 50 by 50, similar to the grid.
     * TODO: WRITE BUTTON INPUT
     */
    public void drawPipeButton(Graphics g, String pipeName, int x, int y, int rotation){
        g.drawRect(xOffset+400+x,yOffset+y,50,50);
        
        //This is the reason there are extra Empty source images. Easier to have a few duplicate files than check a bunch of
        //Class names every time we render.
        String fileName = "images/" + pipeName + "Empty" + Integer.toString(rotation)+".png";
        System.out.println(fileName);
        ImageIcon image = new ImageIcon(fileName);
        image.paintIcon(this,g,xOffset+400+x+1,yOffset+y+1);
    }
    
    
    
    
    /**
     * Called whenever a menu opens.
     */
    public void menuSelected(MenuEvent e){
    }
    
    /**
     * Called whenever a menu is closed. We repaint at this time to undo the erasure of the bit it covered up.
     */
    public void menuDeselected(MenuEvent e){
        repaint();
    }
    
    /**
     * Called whenever a menu is.. canceled? I don't know what this means, and it has never been triggered during testing. ???
     * Seems to be for legacy support. Just call deselected, its essentially the same thing.
     */
    public void menuCanceled(MenuEvent e){
        menuDeselected(e);
    }
    
    
    
    /**
     * Below rests the code for managing the simulation. This includes: 
     * TODO: save/load systems
     * TODO: Image management
     * TODO: Pipe placement
     */
    
    /**
     * This will try to load a network from file.
     * it will return true if this is successful and false if not
     * Until save/load is implemented it always returns false.
     */
    public boolean loadNetworkFromFile(){
        ErrorReporter.reportError("No saved network found.");
        return false;
    }
    
    public void setUpNewNetwork(){
        currentNetwork = new PipeNetwork(networkX,networkY);
    }
}

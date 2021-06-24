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
    
    //Simulation Stuff
    PipeNetwork currentNetwork;
    
    //These are the dimensions of the pipe network that will be initialized.
    //In this version, its 8 by 8.
    int networkX = 8;
    int networkY = 8;
    
    //TODO List
    
    //Must do:
    //Make pipes render
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
        setUpWindow();
        setUpMenus();
        renderWindow();
        if(!loadNetworkFromFile()){
            //This piece of code runs a function to attempt to load the previously worked upon network.
            //If that fails, it sets up a blank network.
            setUpNewNetwork();
        }
    }
    
    /**
     * Sets the basic size and title of the window
     */
    public void setUpWindow(){
       setTitle("Water-Woes 15/06/2021");
       
       
       this.getContentPane().setPreferredSize(new Dimension(xDimension,yDimension)); 
       this.setDefaultCloseOperation(EXIT_ON_CLOSE);
       
       //Boom. Penl time
       panel = new JPanel();
       //same size as window
       panel.setPreferredSize(new Dimension(xDimension,yDimension));
       
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
        int xOffset = 8;
        int yOffset = 53;
        g.drawRect(xOffset,yOffset,599,400);
        drawGrid(g, xOffset, yOffset);
        
        //The vague scheme of the 600 by 400 window is that the first 400 or so square pixels are dedicated to the grid. The right 200 by 400 pixels go to the menu.
    }
    
    
    
    /**
     * Here is where the grid and pipes are drawn. They take up a 400 by 400 area on the left of the window.
     */
    public void drawGrid(Graphics g, int xOffset, int yOffset){
        //In this 600 by 400 version of the simulation, the grid should be 8 by 8. Each 50x50 square consists of a 1px border and a 48x48 image of a pipe if there is a pipe in that spot there. 
        //This means the borders are 2px thick in total, from the neighbouring pipes.
        
        //This bit draws the grid lines. 
        
        for(int xCell=0; xCell<networkX; xCell++){
            //for each column
            for(int yCell = 0; yCell<networkY; yCell++){
                //for each row:
                //Draw an empty rectangle at the border of these bits.
                //functionally, xOffset and yOffset are at 0,0. So we add it to every objective coordinate used to draw.
                //Additionally, for each cell we check what cell it is and move 50px for each, as each is 50px wide/tall.
                g.drawRect(xOffset+(xCell*50),yOffset+(yCell*50),50,50);
                
                //This switch statement takes the relevant cell of the PipeGrid and renders a specific color.
                //TODO: replace with an image.
                
                //Saves teh current color, changes the color, fills the rectangle, and changes it back.
                
                //Pipe = blue
                //Junction = yellow
                //Source = green
                //Sink = red
                //NullPipes aren't drawn.
                
                Color defaultCol = g.getColor();
                
                switch(currentNetwork.pipeGrid[xCell][yCell].getClass().getName()){
                    case "Pipe":
                    case "CornerPipe":
                        //Pipe and CornerPipe use the same color for now. TOODO: Seperate these.
                        g.setColor(Color.BLUE);
                        g.fillRect(xOffset+(xCell*50)+1,yOffset+(yCell*50)+1,49,49);
                        break;
                    case "Junction":
                        g.setColor(Color.YELLOW);
                        g.fillRect(xOffset+(xCell*50)+1,yOffset+(yCell*50)+1,49,49);
                        break;
                    case "Source":
                        g.setColor(Color.GREEN);
                        g.fillRect(xOffset+(xCell*50)+1,yOffset+(yCell*50)+1,49,49);
                        break;
                    case "Sink":
                        g.setColor(Color.RED);
                        g.fillRect(xOffset+(xCell*50)+1,yOffset+(yCell*50)+1,49,49);
                        break;
                    case "NullPipe":
                        break;
                    default:
                        ErrorReporter.reportError("Trying to render invalid pipetype at " + xCell + "," + yCell +".");
                }
                g.setColor(defaultCol);
            }
        }
        
    }
    
    
    /**
     * Called whenever a menu opens.
     */
    public void menuSelected(MenuEvent e){
        ErrorReporter.reportError("Selected");
    }
    
    /**
     * Called whenever a menu is closed. We repaint at this time to undo the erasure of the bit it covered up.
     */
    public void menuDeselected(MenuEvent e){
        ErrorReporter.reportError("Deselected");
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

import javax.swing.*;
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
public class WWWindow extends JFrame implements ActionListener
{
    JMenuBar menuBar;
    JMenu file;
    JMenu pipes;
    JPanel panel;
    int xDimension = 600;
    int yDimension = 400;
    
    Canvas graphic;
    
    /**
     * Constructor for objects of class WWWindow. TODO: Fill this with setup code.
     */
    public WWWindow()
    {
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
       panel = new JPanel();
       //same size as window
       panel.setPreferredSize(new Dimension(xDimension,yDimension));
    
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
    }
    
    public void paint(Graphics g){
        //This infuriates me, frankly. I have to set all the sizes manually! MANUALLY! 
        //Its real frustrating, and I have to set the window to unresizable so taht people don't resize and goof it up!
        
        super.paint(g);
        
        //Boundary code. This should be a thin black line around the edge of the screen if the drawing area is 600 by 400.
        //The offsets are based on the menu height and some weird factor that means an x of 0 is 8 pixels to the left of the window.
        int xOffset = 8;
        int yOffset = 53;
        g.drawRect(xOffset,yOffset,599,400);
        
        //The vague scheme of the 600 by 400 window is that the first 400 or so square pixels are dedicated to the grid. The right 200 by 400 pixels go to the menu.
    }
    
    
    
    public void drawGrid(Graphics g, int xOffset, int yOffset){
        //Here is where the grid and pipes are drawn.
        //In this 600 by 400 version of the simulation, the grid is 4 by 4. Each square consists of a 1px border and a 98x98 image of a pipe if there is one there. 
        //This means the borders are 2px thick in total, from the neighbouring pipes.
    }
}

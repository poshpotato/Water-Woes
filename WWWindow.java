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
public class WWWindow extends JFrame
{
    JMenuBar menuBar;
    JMenu file;
    JMenu pipes;
    
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
       this.getContentPane().setPreferredSize(new Dimension(600,400)); 
       this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        file.add(save);
        JMenuItem newNetw = new JMenuItem("New");
        file.add(newNetw);
        JMenuItem quit = new JMenuItem("Quit");
        file.add(quit);
        
        //Pipes menu. Changes selected Pipe.
        pipes = new JMenu("Pipes");
        menuBar.add(pipes);
        JMenuItem pipe = new JMenuItem("Pipe");
        pipes.add(pipe);
        JMenuItem junction = new JMenuItem("Junction");
        pipes.add(junction);
        JMenuItem source = new JMenuItem("Source");
        pipes.add(source);
        JMenuItem sink = new JMenuItem("Sink");
        pipes.add(sink);
    }
}

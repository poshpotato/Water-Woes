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
       this.getContentPane().setPreferredSize(new Dimension(600,400)); 
       this.setDefaultCloseOperation(EXIT_ON_CLOSE);
       
       //Boom. Penl time
       //panel = new JPanel();
       //same size as window
       //panel.setPreferredSize(new Dimension(400,400));
       //this doesnt work, actually, it needs to go UNDER the window. A quick google says this should work, maybe?
       //Following https://www.guru99.com/java-swing-gui.html#3
       //panel = new JPanel(new GridBagLayout());
       //GridBagConstraints c = new GridBagConstraints();
       //This should make it fill the entire display area. THis is good for now, but I want to add a menu to select pipes to te side later.
       //c.fill = GridBagConstraints.BOTH;
    
       Canvas graphic = new Canvas();
       this.getContentPane().add(BorderLayout.CENTER, graphic);
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
        this.getContentPane().add(BorderLayout.NORTH,menuBar);
        
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
        super.paint(g);
        g.drawRect(0,0,399,399);
    }
}

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
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
public class WWWindow extends JFrame implements ActionListener, MenuListener,MouseListener
{
    
    //Window stuff
    JMenuBar menuBar;
    JMenu file;
    JMenu pipes;
    JPanel panel;
    int xDimension = 600;
    int yDimension = 400;
    
    
    int xOffset = 8;
    int yOffset = 53;
    
    //Simulation Stuff
    PipeNetwork currentNetwork;
    
    //These are the dimensions of the pipe network that will be initialized.
    //In this version, its 8 by 8.
    int networkX = 8;
    int networkY = 8;
    
    //This records the String of whatever pipe you currently have selected.
    //In the format TypeEmptyRotation
    //Its always empty because string parsing weirdness. its fine.
    //"None" is the default value and is used to indicate nothing being selected.
    String selectedPipe = "None";
    
    //TODO List
    
    //Must do:
    //Implement saving
    
    //Lower priority:
    //Move rendering system to seperate panel with custom paintComponent methods.
    //Add option in menu for no pipe/remove pipe.
    
    //If i have copious amounts of time left over:
    //Make cursor change when a pipe is selected.
    //Let the screen scale to 2 or 3 times size.
    
    
    /**
     * Constructor for objects of class WWWindow. 
     */
    public WWWindow()
    {
        if(loadNetworkFromFile()){
            //This piece of code runs a function to attempt to load the previously worked upon network.
            //If that works, it has to calculate with the loaded pipes to ensure it displays correctly.
            currentNetwork.determineFlowRates();
        }else{
            //If that fails, it sets up a blank network.
            setUpNewNetwork();
        }
        //testLoad();
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
       this.getContentPane().addMouseListener(this);
       this.setDefaultCloseOperation(EXIT_ON_CLOSE);
       
       //Boom. Panel time
       //panel = new JPanel(new GridBagLayout());
       //this is the same size as the content page. This isn't best practice. 
       //panel.setPreferredSize(new Dimension(xDimension,yDimension));
       //this.add(panel);
       
       //I have had to do far too much hardcoding in my GUI code to let someone resize and fuck it all up!
       this.setResizable(false);
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
        //TODO: Add functionality for New option.
        JMenuItem newNetw = new JMenuItem("New");
        newNetw.addActionListener(this);
        file.add(newNetw);
        JMenuItem quit = new JMenuItem("Quit");
        quit.addActionListener(this);
        file.add(quit);
        
        //Pipes menu. Changes selected Pipe.
        /*pipes = new JMenu("Pipes");
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
        pipes.add(sink);*/
        drawPipeMenu();
    }
    
    public void actionPerformed(ActionEvent e){
        
        String cmd = "defaultCmd";
        //To identify the source as a button we check its name, then cast it as a button into a seperate ibject. inelegant AND clunky! two for two!
        if(e.getSource() instanceof JButton){
            //If it is an image button:
            JButton butt = (JButton)e.getSource();
            //first we take out the images/ bit
            //System.out.println(butt.getName());
            String[] parts = butt.getName().split("/");
            parts = parts[1].split("\\.");
            //System.out.println(Integer.toString(parts.length));
            cmd = parts[0];
            //Now we're left with something like SourceEmpty0.
            //We then set the currently selected pipe to this.
            selectedPipe = cmd;
        } else{
            cmd = e.getActionCommand();
            //Switch statement to identify option selected.
            //Handles menus.
            switch(cmd){
                case "Quit":
                    System.exit(0);
                    break;
                case "Save":
                    saveNetwork();
                    break;
                case "New":
                    setUpNewNetwork();
                    repaint();
                    break;
                default:
                    ErrorReporter.reportError("Invalid Option \"" + cmd + "\".");
                    break;
            }
        }
        
        //This also handles buttons. All the buttons are image buttons; their name is the file path.
        
        
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
                
                //System.out.println(fileName);
                
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
    public void drawPipeMenu(){
        //we use seperate for loops, just to simplify some of the code-writing.
        
        //The x values are 10, 70, and 130,just to give 10px of space between the 50px images.
            
        //Source for loop:
        for(int i=0;i<4;i++){
            drawPipeButton("Source",10,i*50,i);
        }
        
        //Sink for loop:
        for(int i=0;i<4;i++){
            drawPipeButton("Sink",70,i*50,i);
        }
        
        //Pipe for loop:
        //(It's only two labels)
        for(int i=0;i<2;i++){
            drawPipeButton("Pipe",130,i*50,i);
        }
        
        //XJunction button only has to be drawn once.
        drawPipeButton("XJunction",130,100,0);
        
        //CornerPipe for loop:
        for(int i=0;i<4;i++){
            drawPipeButton("CornerPipe",30,200+(i*50),i);
        }
        
        //Junction for loop:
        for(int i=0;i<4;i++){
            drawPipeButton("Junction",110,200+(i*50),i);
        }
        
        //Last element added appears on to fill entire frame for some reason, covering everything else.
        //So we add an invisible button to cover the screen
        //I really really really don't like this, and if I had more time I would absolutely set up a proper layout.
        JButton emptyButton = new JButton();
        emptyButton.setVisible(false);
        this.add(emptyButton);
    }
    
    /*
     * this was an ill-fated other menu design, built around 3 7-high columns.
    public void drawPipeMenu2(Graphics g){
        //we use seperate for loops, just to simplify some of the code-writing.
        
        //The x values are 10, 70, and 130,just to give 10px of space between the 50px images.
            
        //Source for loop:
        for(int i=0;i<4;i++){
            drawPipeButton(g,"Source",10,5+(i*55),i);
        }
        
        //Sink for loop:
        for(int i=0;i<4;i++){
            drawPipeButton(g,"Sink",70,5+(i*55),i);
        }
        
        //Pipe for loop:
        //(It's only two labels)
        for(int i=0;i<2;i++){
            drawPipeButton(g,"Pipe",130,5+(i*55),i);
        }
        
        //XJunction button only has to be drawn once.
        drawPipeButton(g,"XJunction",130,5+(110),0);
        
        //CornerPipes are drawn differently so custom code.
        drawPipeButton(g,"CornerPipe",10,225,0);
        drawPipeButton(g,"CornerPipe",10,280,1);
        drawPipeButton(g,"CornerPipe",70,225,2);
        drawPipeButton(g,"CornerPipe",70,280,3);
        
        //Junction for loop:
        for(int i=0;i<4;i++){
            drawPipeButton(g,"Junction",130,170+(i*55),i);
        }
        
    }*/
    
    /**
     * Seperate method to draw a given button for ease of reading and writing.
     * x and y are the distances from the top left of the menu area.
     * Pipe Buttons should be 50 by 50, similar to the grid.
     */
    public void drawPipeButton(String pipeName, int x, int y, int rotation){
        //g.drawRect(xOffset+400+x,yOffset+y,50,50);
        
        //This is the reason there are extra Empty source images. Easier to have a few duplicate files than check a bunch of
        //Class names every time we render.
        String fileName = "images/" + pipeName + "Empty" + Integer.toString(rotation)+".png";
        ImageIcon image = new ImageIcon(fileName);
        //image.paintIcon(this,g,xOffset+400+x+1,yOffset+y+1);
        JButton pipeButton = new JButton(image);
        pipeButton.setBounds(xOffset+400+x+1,y+1,50,50);
        //For the buttons to do anything, they have to have an action listener and be named.
        //For simplicity they're named by their filenames.
        pipeButton.addActionListener(this);
        pipeButton.setName(fileName);
        this.add(pipeButton);
    } 
    
     
    
    
    
    
    /**
     * Below rests the code for managing the simulation. This includes: 
     * TODO: save/load systems
     * TODO: Pipe placement
     */
    
    /**
     * This will try to load a network from file.
     * it will return true if this is successful and false if not
     * Until save/load is implemented it always returns false.
     * Saves are stored in the save.csv file.
     */
    public boolean loadNetworkFromFile(){        
        Scanner fileRead;
        try{
             fileRead = new Scanner(new File("save.csv"));
        } catch (FileNotFoundException e){
            //This means that theres not a file to load from.
            ErrorReporter.reportError("No saved network found.");
            return false;
        }
        
        //If the Scanner is set up well, its now connected to the csv.
        //First we get an array of strings representing the lines of the csv, which in turn
        //represent the rows of the grid.
        String[] saveLines = new String[networkY];
        //then we fill this array. Constrained by there being lines and the linecount theres supposed to be.
        int lineCount = 0;
        while(fileRead.hasNextLine() && lineCount < networkY){
            saveLines[lineCount] = fileRead.nextLine();
            if(lineCount<2){System.out.println(saveLines[lineCount]);}
            lineCount++;
        }
        fileRead.close();
        
        //At this point lineCount should be the same as networkY. If it isn't, something's gone wrong with the save file.
        if(lineCount != networkY){
            ErrorReporter.reportError("Incorrent line count in save file. Save file should have "
            + networkY + " lines, but has " + lineCount + "instead.");
            return false;
        }
        
        //new variable to track the elements on each line.
        int elementCount = 0;
        
        //next, we create a pipeNetwork based on this.
        
        PipeNetwork loadNetwork = new PipeNetwork(networkX,networkY);
        
        for(int i = 0; i<networkY; i++){
            //for each line:
            //We split the row elements
            String[] rowElements = saveLines[i].split(",");
            
            //Then we check theres the right amount of elements in each row
            if(rowElements.length != networkX){
                ErrorReporter.reportError("Incorrect element count in save file. Save file row " + i + "should have "
                + networkX + " lines, but has " + rowElements.length + "instead.");
                return false;
            }
            
            //Then, we read with another for loop.
            for(int j = 0; j<networkX; j++){
                //Where i is the row number and j is the column:
                //Well, we need to extract the rotation. This should be a single-character int at the end of the string.
                String name = rowElements[j].substring(0,rowElements[j].length() - 1);
                int rotCur = 0;
                try{
                    rotCur = Integer.parseInt(String.valueOf(rowElements[j].charAt(rowElements[j].length()-1)));
                }catch(NumberFormatException e){
                    ErrorReporter.reportError("Invalid rotation in save file: " + String.valueOf(rowElements[j].charAt(rowElements[j].length()-1)));
                    return false;
                }
                
                if(!name.equals("NullPipe")){loadNetwork.addPipe(name, j, i, rotCur);}
                
                //System.out.println(name + ", " + rotCur);
            }
            
        }
        DebugClass.printNetwork(loadNetwork);
        this.currentNetwork = loadNetwork;
        
        
        return true;
    }
    
    /*public void testLoad(){
         Scanner fileRead = null;
        try{
             fileRead = new Scanner(new File("roll.csv"));
        } catch (FileNotFoundException e){
        }
        
        //If the Scanner is set up well, its now connected to the csv.
        //First we get an array of strings representing the lines of the csv, which in turn
        //represent the rows of the grid.
        String[] saveLines = new String[networkY];
        while(fileRead.hasNextLine()){
            //saveLines[lineCount] = fileRead.nextLine();
            System.out.println(fileRead.nextLine());
            //if(lineCount<2){System.out.println(saveLines[lineCount]);}
            
        }
    
    }*/
    
    public void setUpNewNetwork(){
        currentNetwork = new PipeNetwork(networkX,networkY);
    }
    
    /**
     * This method saves the current network as a csv with pipe names.
     * Parameters: none.
     * Returns: boolean representing the success/failure of the saving process.
     */
    public boolean saveNetwork(){
        //Saves in save.csv. What it has to do is serialize them; 
        //This is something of a problem, as its sorted by column and then row.
        //To solve this we do some weird for loop stuff.
        try{
            File saveFile = new File("save.csv");
            if (saveFile.createNewFile()) {
                System.out.println("File created: " + saveFile.getName());
            } else {
                System.out.println("File " + saveFile.getName() + " already exists.");
                if(saveFile.delete()){
                    saveFile = new File("save.csv");
                } else {
                    ErrorReporter.reportError("Save file could not be created or deleted.");
                }
            }
            
            FileWriter saveWriter = new FileWriter(saveFile);
            
            for(int y = 0; y<networkY; y++){
                String lineBuffer = "";
                for(int x = 0; x<networkX; x++){
                    lineBuffer += currentNetwork.pipeGrid[x][y].getClass().getName();
                    lineBuffer += currentNetwork.pipeGrid[x][y].rotation;
                    lineBuffer += ",";
                }
                //lineBuffer will have an extraneous comma at the end we have to get rid of.
                lineBuffer = lineBuffer.substring(0,lineBuffer.length() - 1);
                //Need to seperate rows by a line seperator.
                System.out.println(lineBuffer);
                saveWriter.write(lineBuffer + "\n");
                //todo make this write to file
            }   
            
            saveWriter.flush();
            saveWriter.close();
        }catch(IOException e){
            ErrorReporter.reportError("Error in file loading: " + e.toString());
        }finally{
            
        }
        
        
        
        
    
        return false;
    }
    
    /**
     * Obligatory inherited event listeners rest below.
     */
    
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
    
    public void mouseClicked(MouseEvent e){
        
    }
    
    public void mousePressed(MouseEvent e){
        //System.out.println("mouseClicked");
        //System.out.println(e.getX() + "," + e.getY());
        //System.out.println("Button " + e.getButton() + " pressed.");
        
        //When the mouse is clicked, we only have to check what its clicking on if its not a button; e.g if its in the grid
        //The first 400x400 pixels of the screen. If its not, return immediately. don't bother running anything else.
        if(!(e.getX() < 400)){
                return;
            }
        //Then we check what button was clicked;
        //Left click places a pipe, right click removes it.
        
        if(e.getButton() == 1){
            //On left click.
            
            //Check if theres no pipe selected. Don't do anything if theres no pipe selected, either.
            if(selectedPipe.equals("None")){
                return;
            }
        
            //Then, we figure out what grid cell  its on. Each is 50x50, so the simplest solution is to:
            //Divide by 50
            //Round down (down so that they match the x and y of the arrays position, e.g. the first cell is at 0,0)
            int gridX = (int)Math.floor(e.getX()/50);
            int gridY = (int)Math.floor(e.getY()/50);
            //System.out.println(gridX + "," + gridY);
        
            //Once we have the position to place a pipe, we need to parse selectedPipe to figure out which to place.
            //The type of pipe is the first x characters. Unfortunately, they have different lengths.
            //Fortunately, substring exists! We take off the last 6 characters (1E-2m-3p-4t-5y-6r where r is the rotation int)
            String placePipeName = selectedPipe.substring(0,selectedPipe.length() - 6);
            //System.out.println(placePipeName);
            //Similarly we get the rotation by getting the last character of the string, and converting it to int.
            int placePipeRotation = Character.getNumericValue(selectedPipe.charAt(selectedPipe.length()-1));
            //System.out.println(placePipeRotation);
        
            //Finally add the actual pipe.
            currentNetwork.addPipe(placePipeName, gridX, gridY, placePipeRotation);        
        
            //Recalculate flow. It does this here because when it's updated is the only time flows will change.
            currentNetwork.determineFlowRates();
            
            //Then, repaint to reflect the changes.
            repaint();
        
            
        } else if(e.getButton() == 3){
            //On right click
            //Get what position it is. Maths explained above, not going to explain it again.
            int gridX = (int)Math.floor(e.getX()/50);
            int gridY = (int)Math.floor(e.getY()/50);
            currentNetwork.removePipe(gridX,gridY);
            //Then repaint and determine flow rates.
            currentNetwork.determineFlowRates();
            repaint();
        }
    }
    
    public void mouseExited(MouseEvent e){
        
    }
    
    public void mouseEntered(MouseEvent e){
        
    }
    
    public void mouseReleased(MouseEvent e){
        
    }
    
    
}

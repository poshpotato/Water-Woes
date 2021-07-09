
import javax.swing.JOptionPane;
import javax.swing.JFrame;
/**
 * Handles error reporting to console or gui.
 *
 * @Jeb Dudfield
 * @1/06/2021
 */
public class ErrorReporter
{
    

    /**
     * Error reporting method. This exists for easy changing of how errors are reported to the user.
     * Currently, it just takes a string and prints it out in the terminal.
     */
    public static void reportError(String errormsg)
    {
        JOptionPane.showMessageDialog(new JFrame(),errormsg);
        System.out.println(errormsg);
    }
}

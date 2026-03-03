import javax.swing.SwingUtilities;

/**
 * @author Mbathio Lo
 */
public class TicTacToeRunner
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new TicTacToeFrame());
    }
}
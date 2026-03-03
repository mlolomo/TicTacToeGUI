import javax.swing.JButton;
import java.awt.Font;

public class TicTacToeTile extends JButton
{
    private int row;
    private int col;

    public TicTacToeTile(int row, int col)
    {
        super(" ");
        this.row = row;
        this.col = col;
        setFont(new Font("Arial", Font.BOLD, 60));
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }
}
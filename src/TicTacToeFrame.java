import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Mbathio Lo
 */
public class TicTacToeFrame extends JFrame
{
    private static final int ROW = 3;
    private static final int COL = 3;

    private TicTacToeTile[][] board;
    private String[][] gameBoard;
    private String currentPlayer;
    private int moveCnt;

    private static final int MOVES_FOR_WIN = 5;
    private static final int MOVES_FOR_TIE = 7;

    private JLabel statusLabel;

    public TicTacToeFrame()
    {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 580);
        setLocationRelativeTo(null);
        setResizable(false);

        board = new TicTacToeTile[ROW][COL];
        gameBoard = new String[ROW][COL];

        buildUI();
        startNewGame();

        setVisible(true);
    }

    private void buildUI()
    {
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Player X's turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(statusLabel, BorderLayout.NORTH);

        JPanel boardPanel = new JPanel(new GridLayout(ROW, COL, 4, 4));
        boardPanel.setBackground(Color.DARK_GRAY);
        boardPanel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));

        ActionListener tileListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                TicTacToeTile clicked = (TicTacToeTile) e.getSource();
                handleMove(clicked.getRow(), clicked.getCol());
            }
        };

        // Create tiles
        for (int r = 0; r < ROW; r++)
        {
            for (int c = 0; c < COL; c++)
            {
                board[r][c] = new TicTacToeTile(r, c);
                board[r][c].setBackground(Color.WHITE);
                board[r][c].setFocusPainted(false);
                board[r][c].addActionListener(tileListener);
                boardPanel.add(board[r][c]);
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Arial", Font.BOLD, 16));
        quitButton.setPreferredSize(new Dimension(120, 40));
        quitButton.addActionListener(e ->
        {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to quit?",
                    "Quit",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        });
        bottomPanel.add(quitButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    /**
     * Resets the game state
     */
    private void startNewGame()
    {
        currentPlayer = "X";
        moveCnt = 0;

        for (int r = 0; r < ROW; r++)
            for (int c = 0; c < COL; c++)
                gameBoard[r][c] = " ";

        for (int r = 0; r < ROW; r++)
        {
            for (int c = 0; c < COL; c++)
            {
                board[r][c].setText(" ");
                board[r][c].setEnabled(true);
                board[r][c].setBackground(Color.WHITE);
                board[r][c].setForeground(Color.BLACK);
            }
        }

        statusLabel.setText("Player X's turn");
    }

    private void handleMove(int row, int col)
    {
        if (!isValidMove(row, col))
        {
            JOptionPane.showMessageDialog(
                    this,
                    "Already taken! Choose an empty square.",
                    "Invalid Move",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        gameBoard[row][col] = currentPlayer;
        board[row][col].setText(currentPlayer);

        if (currentPlayer.equals("X"))
            board[row][col].setForeground(new Color(30, 100, 200));
        else
            board[row][col].setForeground(new Color(200, 40, 40));

        moveCnt++;

        if (moveCnt >= MOVES_FOR_WIN && isWin(currentPlayer))
        {
            statusLabel.setText("Player " + currentPlayer + " wins!");
            disableAllTiles();
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Player " + currentPlayer + " wins!\nPlay again?",
                    "Game Over - Winner!",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION)
                startNewGame();
            else
                System.exit(0);
            return;
        }

        if (moveCnt >= MOVES_FOR_TIE && isTie())
        {
            statusLabel.setText("It's a tie!");
            disableAllTiles();
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "It's a tie! No winner this round.\nPlay again?",
                    "Game Over - Tie!",
                    JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION)
                startNewGame();
            else
                System.exit(0);
            return;
        }

        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        statusLabel.setText("Player " + currentPlayer + "'s turn");
    }

    private void disableAllTiles()
    {
        for (int r = 0; r < ROW; r++)
            for (int c = 0; c < COL; c++)
                board[r][c].setEnabled(false);
    }

    private boolean isValidMove(int row, int col)
    {
        return gameBoard[row][col].equals(" ");
    }

    private boolean isWin(String player)
    {
        return isRowWin(player) || isColWin(player) || isDiagonalWin(player);
    }

    private boolean isRowWin(String player)
    {
        for (int r = 0; r < ROW; r++)
        {
            if (gameBoard[r][0].equals(player) &&
                    gameBoard[r][1].equals(player) &&
                    gameBoard[r][2].equals(player))
                return true;
        }
        return false;
    }

    private boolean isColWin(String player)
    {
        for (int c = 0; c < COL; c++)
        {
            if (gameBoard[0][c].equals(player) &&
                    gameBoard[1][c].equals(player) &&
                    gameBoard[2][c].equals(player))
                return true;
        }
        return false;
    }

    private boolean isDiagonalWin(String player)
    {
        if (gameBoard[0][0].equals(player) &&
                gameBoard[1][1].equals(player) &&
                gameBoard[2][2].equals(player))
            return true;

        if (gameBoard[0][2].equals(player) &&
                gameBoard[1][1].equals(player) &&
                gameBoard[2][0].equals(player))
            return true;

        return false;
    }

    private boolean isTie()
    {
        boolean xFlag;
        boolean oFlag;

        for (int r = 0; r < ROW; r++)
        {
            xFlag = false;
            oFlag = false;
            for (int c = 0; c < COL; c++)
            {
                if (gameBoard[r][c].equals("X")) xFlag = true;
                if (gameBoard[r][c].equals("O")) oFlag = true;
            }
            if (!(xFlag && oFlag)) return false;
        }

        for (int c = 0; c < COL; c++)
        {
            xFlag = false;
            oFlag = false;
            for (int r = 0; r < ROW; r++)
            {
                if (gameBoard[r][c].equals("X")) xFlag = true;
                if (gameBoard[r][c].equals("O")) oFlag = true;
            }
            if (!(xFlag && oFlag)) return false;
        }

        xFlag = false;
        oFlag = false;
        for (int i = 0; i < 3; i++)
        {
            if (gameBoard[i][i].equals("X")) xFlag = true;
            if (gameBoard[i][i].equals("O")) oFlag = true;
        }
        if (!(xFlag && oFlag)) return false;

        xFlag = false;
        oFlag = false;
        if (gameBoard[0][2].equals("X") || gameBoard[1][1].equals("X") || gameBoard[2][0].equals("X")) xFlag = true;
        if (gameBoard[0][2].equals("O") || gameBoard[1][1].equals("O") || gameBoard[2][0].equals("O")) oFlag = true;
        if (!(xFlag && oFlag)) return false;

        return true;
    }
}
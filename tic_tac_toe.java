import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A simple Tic-Tac-Toe game using Java Swing (JFrame).
 */
public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private boolean gameOver = false;

    public TicTacToe() {
        setTitle("Tic-Tac-Toe");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        // Initialize 3x3 grid of buttons
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton(" ");
                buttons[row][col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                buttons[row][col].addActionListener(this);
                add(buttons[row][col]);
            }
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        JButton clicked = (JButton) e.getSource();
        if (!clicked.getText().equals(" ")) {
            // Already marked
            return;
        }

        // Mark the button and check game state
        clicked.setText(String.valueOf(currentPlayer));
        if (checkWin(currentPlayer)) {
            JOptionPane.showMessageDialog(this, currentPlayer + " wins!");
            gameOver = true;
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            gameOver = true;
        } else {
            // Switch player
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private boolean checkWin(char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().charAt(0) == player &&
                buttons[i][1].getText().charAt(0) == player &&
                buttons[i][2].getText().charAt(0) == player) {
                return true;
            }
            if (buttons[0][i].getText().charAt(0) == player &&
                buttons[1][i].getText().charAt(0) == player &&
                buttons[2][i].getText().charAt(0) == player) {
                return true;
            }
        }
        // Diagonals
        if (buttons[0][0].getText().charAt(0) == player &&
            buttons[1][1].getText().charAt(0) == player &&
            buttons[2][2].getText().charAt(0) == player) {
            return true;
        }
        if (buttons[0][2].getText().charAt(0) == player &&
            buttons[1][1].getText().charAt(0) == player &&
            buttons[2][0].getText().charAt(0) == player) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Run the game
        SwingUtilities.invokeLater(() -> new TicTacToe());
    }
}

/*
 * Breakdown of the program:
 *
 * 1. Class & Imports:
 *    - We import Swing and AWT packages for GUI components and event handling.
 *    - The TicTacToe class extends JFrame to create a window and implements ActionListener
 *      to respond to button clicks.
 *
 * 2. GUI Setup (Constructor):
 *    - We set up JFrame properties: title, size, close operation, and layout (3x3 grid).
 *    - Create a 3x3 grid of JButtons, set a large font, add action listeners, and add them to the frame.
 *
 * 3. Handling Moves (actionPerformed):
 *    - When a button is clicked, ensure the game isn't over and the button is empty.
 *    - Mark the button with the current player's symbol ("X" or "O").
 *    - Check for a win or draw, show a dialog if game ends, or switch players.
 *
 * 4. Game Logic:
 *    - checkWin(player): Verifies rows, columns, and diagonals for three-in-a-row.
 *    - isBoardFull(): Checks if all buttons are marked (to detect a draw).
 *
 * 5. Main Method:
 *    - Launches the GUI on the Event Dispatch Thread.
 *
 * This simple structure separates GUI setup, event handling, and game logic,
 * making it easy to maintain and extend (e.g., adding a reset button or AI opponent).
 */

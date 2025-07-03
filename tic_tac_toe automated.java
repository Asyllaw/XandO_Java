import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Enhanced Tic-Tac-Toe with player alias input, automated opponent, and random move logic.
 */
public class TicTacToe extends JFrame implements ActionListener {
    private JButton[][] buttons = new JButton[3][3];
    private String playerX, playerO;
    private char currentPlayer = 'X';
    private boolean gameOver = false;
    private JLabel statusLabel;
    private boolean playWithBot = false;
    private Random random = new Random();

    public TicTacToe() {
        showIntroPage();
    }

    private void showIntroPage() {
        JTextField xField = new JTextField();
        JCheckBox vsBot = new JCheckBox("Play against computer");
        Object[] message = {
            "Enter Your Name:", xField,
            vsBot
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Tic-Tac-Toe Setup", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            playerX = xField.getText().isEmpty() ? "Player X" : xField.getText();
            playWithBot = vsBot.isSelected();
            playerO = playWithBot ? "Computer" : "Player O";
            setupGameBoard();
        } else {
            System.exit(0);
        }
    }

    private void setupGameBoard() {
        setTitle("Tic-Tac-Toe");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton(" ");
                buttons[row][col].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 60));
                buttons[row][col].addActionListener(this);
                boardPanel.add(buttons[row][col]);
            }
        }

        statusLabel = new JLabel(getCurrentPlayerName() + "'s turn", SwingConstants.CENTER);
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statusLabel, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);

        add(boardPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private String getCurrentPlayerName() {
        return currentPlayer == 'X' ? playerX : playerO;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) return;

        JButton clicked = (JButton) e.getSource();
        if (!clicked.getText().equals(" ")) return;

        clicked.setText(String.valueOf(currentPlayer));

        if (checkGameState()) return;

        // Switch player
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        statusLabel.setText(getCurrentPlayerName() + "'s turn");

        // If it's the bot's turn, let it play
        if (playWithBot && currentPlayer == 'O' && !gameOver) {
            botPlay();
        }
    }

    private boolean checkGameState() {
        if (checkWin(currentPlayer)) {
            statusLabel.setText(getCurrentPlayerName() + " wins!");
            JOptionPane.showMessageDialog(this, getCurrentPlayerName() + " wins!");
            gameOver = true;
            return true;
        } else if (isBoardFull()) {
            statusLabel.setText("It's a draw!");
            JOptionPane.showMessageDialog(this, "It's a draw!");
            gameOver = true;
            return true;
        }
        return false;
    }

    private void botPlay() {
        // Collect available moves
        ArrayList<JButton> available = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals(" ")) {
                    available.add(buttons[row][col]);
                }
            }
        }

        if (!available.isEmpty()) {
            JButton choice = available.get(random.nextInt(available.size()));
            choice.setText("O");

            if (checkGameState()) return;

            currentPlayer = 'X';
            statusLabel.setText(getCurrentPlayerName() + "'s turn");
        }
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().charAt(0) == player &&
                buttons[i][1].getText().charAt(0) == player &&
                buttons[i][2].getText().charAt(0) == player) return true;

            if (buttons[0][i].getText().charAt(0) == player &&
                buttons[1][i].getText().charAt(0) == player &&
                buttons[2][i].getText().charAt(0) == player) return true;
        }

        if (buttons[0][0].getText().charAt(0) == player &&
            buttons[1][1].getText().charAt(0) == player &&
            buttons[2][2].getText().charAt(0) == player) return true;

        if (buttons[0][2].getText().charAt(0) == player &&
            buttons[1][1].getText().charAt(0) == player &&
            buttons[2][0].getText().charAt(0) == player) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col].getText().equals(" ")) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TicTacToe());
    }
}

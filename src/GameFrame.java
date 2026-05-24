import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private GamePanel gamePanel;
    private GameOverPanel gameOverPanel;

    public GameFrame() {
        this.setTitle("PokePanic");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // יצירת המסכים
        MenuPanel menuPanel = new MenuPanel(this);
        gamePanel = new GamePanel(this);
        gameOverPanel = new GameOverPanel(this);
        HelpPanel helpPanel = new HelpPanel(this);

        // הוספת המסכים למנהל התצוגה
        mainContainer.add(menuPanel, "MENU_SCREEN");
        mainContainer.add(gamePanel, "GAME_SCREEN");
        mainContainer.add(gameOverPanel, "GAMEOVER_SCREEN");
        mainContainer.add(helpPanel, "HELP_SCREEN");

        this.add(mainContainer);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        showMenuScreen();
    }

    public void showMenuScreen() {
        cardLayout.show(mainContainer, "MENU_SCREEN");
    }

    public void startGame() {
        cardLayout.show(mainContainer, "GAME_SCREEN");
        gamePanel.startGameLoop();
        gamePanel.requestFocusInWindow();
    }

    public void showGameOverScreen(int score) {
        if (gamePanel != null) {
            gamePanel.stopGameLoop();
        }
        gameOverPanel.setFinalScore(score);
        cardLayout.show(mainContainer, "GAMEOVER_SCREEN");
    }

    public void showHelpScreen() {
        cardLayout.show(mainContainer, "HELP_SCREEN");
    }
}
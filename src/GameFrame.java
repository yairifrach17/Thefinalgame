import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private GamePanel gamePanel;
    private GameOverPanel gameOverPanel; // <-- שונה למשתנה מחלקה כדי שנוכל להעביר לו את הניקוד

    public GameFrame() {
        this.setTitle("PokePanic");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // יצירת המסכים
        MenuPanel menuPanel = new MenuPanel(this);
        gamePanel = new GamePanel(this);
        gameOverPanel = new GameOverPanel(this); // <-- האתחול משתמש במשתנה של המחלקה למעלה
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

    // <-- הפונקציה המעודכנת שמקבלת את הניקוד ומעדכנת את מסך הפסילה
    public void showGameOverScreen(int score) {
        if (gamePanel != null) {
            gamePanel.stopGameLoop();
        }
        gameOverPanel.setFinalScore(score); // מעביר את הניקוד שנקלט למסך הפסילה
        cardLayout.show(mainContainer, "GAMEOVER_SCREEN");
    }

    public void showHelpScreen() {
        cardLayout.show(mainContainer, "HELP_SCREEN");
    }
}
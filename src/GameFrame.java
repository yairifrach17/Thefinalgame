import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private GamePanel gamePanel;

    public GameFrame() {
        this.setTitle("בהלה במטבח - Kitchen Panic");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // זה מה שמאפשר לאיקס לעבוד!

        // יצירת מנהל התצוגה
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // יצירת המסכים (שים לב שעכשיו אנחנו שוב קוראים ל-GamePanel הנפרד)
        MenuPanel menuPanel = new MenuPanel(this);
        gamePanel = new GamePanel(this);
        GameOverPanel gameOverPanel = new GameOverPanel(this);

        // הוספה לקונטיינר
        mainContainer.add(menuPanel, "MENU_SCREEN");
        mainContainer.add(gamePanel, "GAME_SCREEN");
        mainContainer.add(gameOverPanel, "GAMEOVER_SCREEN");

        this.add(mainContainer);

        // פתיחת החלון על כל המסך אבל עם מסגרת (כדי שיהיה איקס!)
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
        gamePanel.requestFocusInWindow();
    }

    public void showGameOverScreen() {
        cardLayout.show(mainContainer, "GAMEOVER_SCREEN");
    }
}
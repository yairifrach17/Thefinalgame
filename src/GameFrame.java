import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private GamePanel gamePanel;

    public GameFrame() {
        this.setTitle("בהלה במטבח - Kitchen Panic");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        MenuPanel menuPanel = new MenuPanel(this);
        gamePanel = new GamePanel(this);
        GameOverPanel gameOverPanel = new GameOverPanel(this);

        mainContainer.add(menuPanel, "MENU_SCREEN");
        mainContainer.add(gamePanel, "GAME_SCREEN");
        mainContainer.add(gameOverPanel, "GAMEOVER_SCREEN");

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
        gamePanel.startGameLoop(); // <--- אומרים לפאנל: תתחיל את המשחק!
        gamePanel.requestFocusInWindow();
    }

    public void showGameOverScreen() {
        gamePanel.stopGameLoop(); // <--- עוצרים את החפצים כשנפסלים
        cardLayout.show(mainContainer, "GAMEOVER_SCREEN");
    }
}
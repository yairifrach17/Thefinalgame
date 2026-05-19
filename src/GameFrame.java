import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class GameFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private GamePanel gamePanel; // רפרנס לפאנל של חבר הצוות

    public GameFrame() {
        this.setTitle("בהלה במטבח - Kitchen Panic");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);

        // יצירת מנהל התצוגה שיודע לדפדף בין מסכים
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // יצירת המופעים של כל המסכים
        MenuPanel menuPanel = new MenuPanel(this);
        gamePanel = new GamePanel(this); // זה הפאנל שחבר הצוות כתב
        GameOverPanel gameOverPanel = new GameOverPanel(this);

        // הוספת המסכים לקונטיינר הראשי עם "שם" מזהה
        mainContainer.add(menuPanel, "MENU_SCREEN");
        mainContainer.add(gamePanel, "GAME_SCREEN");
        mainContainer.add(gameOverPanel, "GAMEOVER_SCREEN");

        // הוספת הקונטיינר הראשי לחלון
        this.add(mainContainer);

        this.setLocationRelativeTo(null); // מרכוז
        this.setVisible(true);

        // מתחילים תמיד מהתפריט הראשי
        showMenuScreen();
    }

    public void showMenuScreen() {
        cardLayout.show(mainContainer, "MENU_SCREEN");
    }

    public void startGame() {
        // 1. העברת התצוגה למסך המשחק
        cardLayout.show(mainContainer, "GAME_SCREEN");

        // 2. מיקוד הפאנל כדי שהמקלדת (KeyListener) תעבוד
        gamePanel.requestFocusInWindow();

        // 3. הפעלת התהליכון (Thread) שחבר הצוות כתב
        gamePanel.startGame();
    }

    public void showGameOverScreen() {
        // מעבר למסך סיום המשחק במקום רק להדפיס לקונסולה
        cardLayout.show(mainContainer, "GAMEOVER_SCREEN");
    }
}
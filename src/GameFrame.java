import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

/**
 * החלון הראשי של המשחק. יורש מ-JFrame ומנהל את זרימת המסכים (Flow)
 * בעזרת CardLayout במצב מסך מלא.
 */
public class GameFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private GamePanel gamePanel; // רפרנס לפאנל המשחק של חבר הצוות

    public GameFrame() {
        // הגדרת כותרת לחלון (תוצג אם המשחק יורץ במצב חלונאי כגיבוי)
        this.setTitle("בהלה במטבח - Kitchen Panic");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // מעלים את מסגרת החלון הרגילה (כפתורי X, הגדלה ומיזעור) לטובת מסך מלא נקי
        this.setUndecorated(true);
        this.setResizable(false);

        // 1. אתחול מנהל התצוגה (CardLayout) והפאנל הראשי שיכיל את המסכים
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // 2. יצירת המופעים של כל המסכים במשחק והעברת הרפרנס של החלון אליהם (this)
        MenuPanel menuPanel = new MenuPanel(this);
        gamePanel = new GamePanel(this); // הפאנל של חבר הצוות שלך
        GameOverPanel gameOverPanel = new GameOverPanel(this);

        // 3. הוספת המסכים לקונטיינר הראשי עם מפתח String מזהה
        mainContainer.add(menuPanel, "MENU_SCREEN");
        mainContainer.add(gamePanel, "GAME_SCREEN");
        mainContainer.add(gameOverPanel, "GAMEOVER_SCREEN");

        // 4. הוספת הקונטיינר הראשי לתוך ה-JFrame
        this.add(mainContainer);

        // 5. העברה למצב מסך מלא אמיתי באמצעות כרטיס המסך של המחשב
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            // גיבוי במידה והמסך/מערכת ההפעלה לא תומכים במסך מלא בלעדי
            this.setSize(800, 600);
            this.setExtendedState(JFrame.MAXIMIZED_BOTH); // הגדלה למקסימום
            this.setLocationRelativeTo(null); // מרכוז
        }

        // הצגת החלון על המסך
        this.setVisible(true);

        // תחילת ריצה: הצגת תפריט הפתיחה הראשי
        showMenuScreen();
    }

    /**
     * פונקציה פומבית המעבירה את התצוגה למסך התפריט הראשי.
     */
    public void showMenuScreen() {
        cardLayout.show(mainContainer, "MENU_SCREEN");
    }

    /**
     * פונקציה פומבית המופעלת בלחיצה על כפתור התחלת המשחק בתפריט.
     * מעבירה מסך, נותנת פוקוס למקלדת ומפעילה את הלולאה של חבר הצוות.
     */
    public void startGame() {
        // א. העברת התצוגה הויזואלית למסך המשחק
        cardLayout.show(mainContainer, "GAME_SCREEN");

        // ב. בקשת פוקוס לחלון - קריטי כדי שה-KeyListener של המשחק יקלוט הקלדות מיד!
        gamePanel.requestFocusInWindow();

        // ג. הפעלת ה-Thread של המשחק שחבר הצוות שלך כתב
        gamePanel.startGame();
    }

    /**
     * הפונקציה שחבר הצוות שלך קורא לה מתוך GamePanel כשהחיים נגמרים.
     * פונקציה זו הייתה חסרה והיא תפתור את השגיאה האדומה!
     */
    public void showGameOverScreen() {
        System.out.println("Game Over Screen Triggered!");
        cardLayout.show(mainContainer, "GAMEOVER_SCREEN");
    }
} // סוגר מסולסל סופי של כל המחלקה
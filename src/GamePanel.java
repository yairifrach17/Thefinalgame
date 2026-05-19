import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread gameThread;
    private boolean running;

    // ניהול מצב משחק
    private int score;
    private int lives;

    // הגדרות עבור 60 FPS
    private final int FPS = 60;
    private final long TARGET_TIME = 1000 / FPS;

    private GameFrame gameFrame; // רפרנס לחלון הראשי כדי לקרוא למסך סיום

    public GamePanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.score = 0;
        this.lives = 3;

        this.setFocusable(true); // חובה כדי שהפאנל יוכל לקלוט הקלדות
        this.addKeyListener(this);
    }

    public void startGame() {
        if (gameThread == null || !running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    // ניקוי שרשורים: עצירת הלולאה בצורה בטוחה
    public void stopGame() {
        running = false;
        // קריאה לפונקציה במסגרת הראשית כדי להציג את מסך סיום המשחק
        if (gameFrame != null) {
            gameFrame.showGameOverScreen();
        }
    }

    @Override
    public void run() {
        long startTime;
        long waitTime;

        // הלולאה הראשית של המשחק
        while (running) {
            startTime = System.nanoTime();

            update(); // עדכון הלוגיקה של המשחק (מיקומי דמויות וכו')
            repaint(); // ציור מחדש של המסך

            // בדיקת תנאי סיום משחק
            if (lives <= 0) {
                stopGame();
            }

            // חישוב הזמן שנותר כדי לשמור על 60 פריימים בשנייה
            waitTime = TARGET_TIME - ((System.nanoTime() - startTime) / 1000000);
            if (waitTime > 0) {
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void update() {
        // כאן חברי הצוות יוסיפו את העדכונים השוטפים של המשחק
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ציור הניקוד והחיים על המסך
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("Lives: " + lives, 20, 60);

        // כאן חברי הצוות יוסיפו את הציור של האובייקטים עצמם
    }

    // --- הגדרות KeyListener ---
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // בינתיים רק הדפסה לקונסול, חבר הצוות יחבר כאן את תנועת השחקן
        System.out.println("Key Pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
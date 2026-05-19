import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

/**
 * פאנל המשחק הראשי. מנהל את ציור השחקן והתנועה.
 */
public class GamePanel extends JPanel implements KeyListener {

    private GameFrame gameFrame;
    private Image playerImage;
    private int playerSize = 60; // גודל הפוקדור

    // מיקום השחקן (התחלתי - נחשב ב-paintComponent הראשון)
    private int playerX = -1;
    private int playerY = -1;
    private int playerSpeed = 15; // כמה פיקסלים הפוקדור יזוז בכל לחיצה

    // משתני מצב משחק (Getters/Setters למניעת שגיאות ב-CollisionManager)
    private int score = 0;
    private int lives = 3;

    public GamePanel(GameFrame frame) {
        this.gameFrame = frame;

        // --- שינוי 1: רקע לבן ---
        this.setBackground(Color.WHITE);

        // הגדרת פריסה חופשית (null layout) כדי שנוכל למקם את השחקן
        this.setLayout(null);

        // טעינת תמונת הפוקדור
        String pokePath = "Rsc/pokeball.png"; // ודא שהנתיב נכון!
        if (new File(pokePath).exists()) {
            playerImage = new ImageIcon(pokePath).getImage();
        } else {
            System.out.println("שגיאה: תמונת הפוקדור לא נמצאה בנתיב " + pokePath);
        }

        // --- שינוי 2: הסרת כפתור 'דמה פסילה' ---
        // (השורות שיוצרות את ה-failButton נמחקו)

        // --- הכנה לתנועה: הוספת מאזין למקלדת ---
        this.addKeyListener(this);
        this.setFocusable(true); // חובה כדי שהפאנל יקלוט את הלחיצות
    }

    // --- Getters & Setters ---
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; repaint(); }
    public int getLives() { return lives; }
    public void setLives(int lives) {
        this.lives = lives;
        repaint();
        // בדיקה אם המשחק נגמר
        if (this.lives <= 0) {
            gameFrame.showGameOverScreen();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // אתחול מיקום השחקן בפעם הראשונה בלבד
        if (playerX == -1 && playerY == -1) {
            playerX = (getWidth() / 2) - (playerSize / 2); // אמצע אופקי
            playerY = getHeight() - playerSize - 40;        // תחתית אנכית
        }

        // ציור הפוקדור
        if (playerImage != null) {
            g.drawImage(playerImage, playerX, playerY, playerSize, playerSize, this);
        } else {
            // גיבוי: עיגול אדום אם התמונה חסרה
            g.setColor(Color.RED);
            g.fillOval(playerX, playerY, playerSize, playerSize);
        }

        // ציור הניקוד
        g.setColor(Color.BLACK); // שינוי לצבע שחור על רקע לבן
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("Score: " + score + " | Lives: " + lives, 20, 35);
    }

    // --- שינוי 3: מימוש תנועת הפוקדור (KeyListener) ---
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // תנועה למעלה
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
            playerY -= playerSpeed;
        }
        // תנועה למטה
        if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
            playerY += playerSpeed;
        }
        // תנועה שמאלה
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            playerX -= playerSpeed;
        }
        // תנועה ימינה
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            playerX += playerSpeed;
        }

        // --- בדיקת גבולות מסך (Borderless Bounds) ---
        // מונע מהפוקדור לצאת מהמסך
        if (playerX < 0) playerX = 0;
        if (playerY < 0) playerY = 0;
        if (playerX > getWidth() - playerSize) playerX = getWidth() - playerSize;
        if (playerY > getHeight() - playerSize) playerY = getHeight() - playerSize;

        // ציור מחדש של המסך כדי להראות את הפוקדור במיקום החדש
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // אין צורך למימוש
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // אין צורך למימוש
    }
}
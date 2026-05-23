import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class GamePanel extends JPanel implements KeyListener {

    private GameFrame gameFrame;
    private Image playerImage;
    private int playerSize = 100;

    private int playerX = -1;
    private int playerY = -1;
    private int playerSpeed = 30;

    private int score = 0;
    private int lives = 3;

    // --- התוספות שלנו: מנהל החפצים ולולאת המשחק ---
    private ObjectManager objectManager;
    private Thread gameThread;
    private volatile boolean isRunning = false;

    public GamePanel(GameFrame frame) {
        this.gameFrame = frame;
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        String pokePath = "Rsc/pokeball.png";
        if (new File(pokePath).exists()) {
            playerImage = new ImageIcon(pokePath).getImage();
        } else {
            System.out.println("שגיאה: תמונת הפוקדור לא נמצאה בנתיב " + pokePath);
        }

        this.addKeyListener(this);
        this.setFocusable(true);
    }

    // --- הפונקציות שמפעילות ועוצרות את המשחק (נקראות מ-GameFrame) ---
    public void startGameLoop() {
        if (isRunning) return;

        // מכינים את המסך והמנהל
        score = 0;
        lives = 3;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        objectManager = new ObjectManager(screenSize.width, screenSize.height);

        isRunning = true;

        // זה ה"דופק" של המשחק! רץ ברקע כל הזמן
        gameThread = new Thread(() -> {
            int spawnTimer = 0;
            while (isRunning) {
                spawnTimer++;

                // 1. הגרלת חפצים (בערך כל שנייה)
                if (spawnTimer >= 100) {
                    objectManager.spawnObject(score);
                    spawnTimer = 0;
                }

                // 2. ניקוי חפצים שנפלו
                // 2. קודם בודקים אם פספסנו פריט טוב, ואז מנקים חפצים שנפלו
                synchronized (objectManager.getObjectsList()) {
                    for (FallingObject1 obj : objectManager.getObjectsList()) {
                        // בודקים רק חפצים שכבר סיימו לרוץ (או שנתפסו או שנפלו לרצפה)
                        if (!obj.isRunning()) {
                            // אם זה חפץ טוב, וה-Y שלו נמצא ממש למטה (כלומר הוא פגע ברצפה ולא בשחקן)
                            if (obj instanceof GoodIngredient && obj.getY() >= getHeight() - 150) {
                                // מורידים חיים כי פספסנו אותו
                                SwingUtilities.invokeLater(() -> setLives(lives - 1));
                            }
                        }
                    }
                }
// עכשיו אפשר למחוק את כולם בבטחה מהרשימה
                objectManager.removeFinishedObjects();;

                // 3. בדיקת פגיעות בשחקן
                checkCollisions();

                // 4. ציור מחדש של המסך
                repaint();

                // מנוחה של 16 מילישניות (נותן בערך 60 FPS)
                try { Thread.sleep(16); } catch (Exception e) {}
            }
        });
        gameThread.start();
    }

    public void stopGameLoop() {
        isRunning = false;
        if (objectManager != null) {
            objectManager.stopAllObjects();
        }
    }

    // --- בדיקת התנגשויות בין הפוקדור לחפצים ---
    private void checkCollisions() {
        if (playerX == -1) return; // השחקן עדיין לא צויר

        Rectangle playerRect = new Rectangle(playerX, playerY, playerSize, playerSize);

        synchronized (objectManager.getObjectsList()) {
            for (FallingObject1 obj : objectManager.getObjectsList()) {
                if (!obj.isRunning()) continue;

                // יצירת מלבן פגיעה סביב החפץ שנופל
                int w = obj.getWidth() > 0 ? obj.getWidth() : 50;
                int h = obj.getHeight() > 0 ? obj.getHeight() : 50;
                Rectangle objRect = new Rectangle(obj.getX(), obj.getY(), w, h);

                // האם יש פגיעה?
                if (playerRect.intersects(objRect)) {
                    obj.stopFalling(); // מעלים את החפץ

                    // שינוי ניקוד וחיים חייב לקרות דרך ממשק המשתמש (SwingUtilities)
                    SwingUtilities.invokeLater(() -> {
                        if (obj instanceof BadItem) {
                            setLives(lives - 1);
                        } else if (obj instanceof GoodIngredient) {
                            setScore(score + 10);
                        }
                    });
                }
            }
        }
    }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; repaint(); }
    public int getLives() { return lives; }
    public void setLives(int lives) {
        this.lives = lives;
        repaint();
        if (this.lives <= 0) {
            gameFrame.showGameOverScreen();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (playerX == -1 && playerY == -1) {
            playerX = (getWidth() / 2) - (playerSize / 2);
            playerY = getHeight() - playerSize - 40;
        }

        if (playerImage != null) {
            g.drawImage(playerImage, playerX, playerY, playerSize, playerSize, this);
        } else {
            g.setColor(Color.RED);
            g.fillOval(playerX, playerY, playerSize, playerSize);
        }

        // --- הוספנו את הציור של החפצים למסך! ---
        if (objectManager != null) {
            synchronized (objectManager.getObjectsList()) {
                for (FallingObject1 obj : objectManager.getObjectsList()) {
                    obj.draw(g);
                }
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("Score: " + score + " | Lives: " + lives, 20, 35);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
       // if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) playerY -= playerSpeed;
       // if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) playerY += playerSpeed;
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) playerX -= playerSpeed;
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) playerX += playerSpeed;

        if (playerX < 0) playerX = 0;
        if (playerY < 0) playerY = 0;
        if (playerX > getWidth() - playerSize) playerX = getWidth() - playerSize;
        if (playerY > getHeight() - playerSize) playerY = getHeight() - playerSize;

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
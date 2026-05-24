import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class GamePanel extends JPanel implements KeyListener {

    private GameFrame gameFrame;
    private Image playerImage;
    private Image backgroundImage;
    private int playerSize = 100;

    private int playerX = -1;
    private int playerY = -1;
    private int playerSpeed = 25;

    private int score = 0;
    private int lives = 3;
    private javax.sound.sampled.Clip gameMusicClip;

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

        String bgPath = "Rsc/game_bg.png";
        if (new File(bgPath).exists()) {
            backgroundImage = new ImageIcon(bgPath).getImage();
            System.out.println("✅ הצלחה: תמונת הרקע נטענה בהצלחה!");
        } else {
            System.out.println("⚠️ שים לב: תמונת הרקע game_bg.png לא נמצאה בתיקיית Rsc (יוצג רקע לבן).");
        }

        this.addKeyListener(this);
        this.setFocusable(true);
    }

    public void startGameLoop() {
        if (isRunning) return;

        score = 0;
        lives = 3;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        objectManager = new ObjectManager(screenSize.width, screenSize.height);

        isRunning = true;
        playGameMusic("Rsc/game_theme.wav");
        this.requestFocusInWindow();

        gameThread = new Thread(() -> {
            int spawnTimer = 0;
            while (isRunning) {
                spawnTimer++;

                if (spawnTimer >= 100) {
                    objectManager.spawnObject(score);
                    spawnTimer = 0;
                }

                synchronized (objectManager.getObjectsList()) {
                    for (FallingObject obj : objectManager.getObjectsList()) {
                        if (!obj.isRunning()) {
                            if (obj instanceof GoodItem && obj.getY() >= getHeight() - 150) {
                                obj.stopFalling();
                                SwingUtilities.invokeLater(() -> setLives(lives - 1));
                            }
                        }
                    }
                }

                objectManager.removeFinishedObjects();
                checkCollisions();
                repaint();

                try { Thread.sleep(16); } catch (Exception e) {}
            }
        });
        gameThread.start();
    }

    public void stopGameLoop() {
        isRunning = false;
        stopGameMusic();
        if (objectManager != null) {
            objectManager.stopAllObjects();
        }
    }

    private void checkCollisions() {
        if (playerX == -1) return;

        Rectangle playerRect = new Rectangle(playerX, playerY, playerSize, playerSize);

        synchronized (objectManager.getObjectsList()) {
            for (FallingObject obj : objectManager.getObjectsList()) {
                if (!obj.isRunning()) continue;

                int w = obj.getWidth() > 0 ? obj.getWidth() : 50;
                int h = obj.getHeight() > 0 ? obj.getHeight() : 50;
                Rectangle objRect = new Rectangle(obj.getX(), obj.getY(), w, h);

                if (playerRect.intersects(objRect)) {
                    obj.stopFalling();

                    SwingUtilities.invokeLater(() -> {
                        if (obj instanceof BadItem) {
                            setLives(lives - 1);
                        } else if (obj instanceof GoodItem) {
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
            gameFrame.showGameOverScreen(score); // <-- שונה: עכשיו מעביר את הציון הנוכחי לפריימוס
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

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

        if (objectManager != null) {
            synchronized (objectManager.getObjectsList()) {
                for (FallingObject obj : objectManager.getObjectsList()) {
                    obj.draw(g);
                }
            }
        }

        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.drawString("Score: " + score + " | Lives: " + lives, 20, 35);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
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
    // 🎵 פונקציה לטעינה והפעלת מוזיקת המשחק בלולאה
    private void playGameMusic(String filePath) {
        try {
            java.io.File musicFile = new java.io.File(filePath);
            if (musicFile.exists()) {
                javax.sound.sampled.AudioInputStream audioInput = javax.sound.sampled.AudioSystem.getAudioInputStream(musicFile);
                gameMusicClip = javax.sound.sampled.AudioSystem.getClip();
                gameMusicClip.open(audioInput);
                gameMusicClip.loop(javax.sound.sampled.Clip.LOOP_CONTINUOUSLY); // ניגון אינסופי
            } else {
                System.out.println("⚠️ התרעה: קובץ הסאונד למשחק לא נמצא בנתיב: " + filePath);
            }
        } catch (Exception e) {
            System.out.println("שגיאה בטעינת מוזיקת המשחק: " + e.getMessage());
        }
    }

    // 🎵 פונקציה לעצירת המוזיקה וניקוי המשאבים מהזיכרון
    private void stopGameMusic() {
        if (gameMusicClip != null && gameMusicClip.isRunning()) {
            gameMusicClip.stop();
            gameMusicClip.close();
        }
    }
}
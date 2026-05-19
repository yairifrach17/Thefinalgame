import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private Thread gameThread;
    private boolean running;

    // ניהול מצב משחק (משתנים של חבר שלך)
    private int score;
    private int lives;

    // הגדרות עבור 60 FPS
    private final int FPS = 60;
    private final long TARGET_TIME = 1000 / FPS;

    private GameFrame gameFrame;

    // === אובייקטים של המשחק (התוספת שלך) ===
    private Chef chef;
    private ArrayList<FallingObject> fallingObjects;

    public GamePanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.score = 0;
        this.lives = 3;

        // אתחול השף (רוחב מסך 800) ורשימת החפצים
        this.chef = new Chef(370, 480, 800);
        this.fallingObjects = new ArrayList<>();

        this.setFocusable(true);
        this.addKeyListener(this);
    }

    public void startGame() {
        if (gameThread == null || !running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void stopGame() {
        running = false;
        if (gameFrame != null) {
            gameFrame.showGameOverScreen();
        }
    }

    @Override
    public void run() {
        long startTime;
        long waitTime;

        while (running) {
            startTime = System.nanoTime();

            update();
            repaint();

            if (this.lives <= 0) {
                stopGame();
            }

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

    // === עדכון הלוגיקה וההתנגשויות ===
    private void update() {
        // 1. עדכון תנועת החפצים הנופלים
        for (FallingObject obj : fallingObjects) {
            obj.update();
        }

        // 2. קריאה למערכת ההתנגשויות שלך
        CollisionManager.checkCollisions(chef, fallingObjects, this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // ציור הניקוד והחיים של חבר שלך
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 20, 30);
        g.drawString("Lives: " + lives, 20, 60);

        // === ציור הדמויות שלך ===
        // ציור השף
        if (chef != null) {
            chef.draw(g);
        }

        // ציור החפצים הנופלים
        for (FallingObject obj : fallingObjects) {
            obj.draw(g);
        }
    }

    // --- הגדרות KeyListener המעודכנות לתנועת השף ---
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // חצים או מקשי A/D מזיזים את השף
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
            chef.moveLeft();
        }
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
            chef.moveRight();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    // גטרים וסטטרים פשוטים כדי שתוכל לעדכן את הניקוד והחיים מתוך CollisionManager
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
}
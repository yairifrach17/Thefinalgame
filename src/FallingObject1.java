import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class FallingObject1 implements Runnable {
    protected int x, y;
    protected int speed;
    protected int delay;
    protected int panelHeight;
    protected volatile boolean running;
    protected BufferedImage image;

    public FallingObject1 (int x, int speed, int delay, int panelHeight, BufferedImage image) {
        this.x = x;
        this.y = -50;
        this.speed = speed;
        this.delay = delay;
        this.panelHeight = panelHeight;
        this.image = image;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            this.y += speed;

            if (y > panelHeight) {
                stopFalling();
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    public void stopFalling() {
        this.running = false;
    }

    public void draw(Graphics g) {
        if (running && image != null) {
            g.drawImage(image, x, y, null);
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return image != null ? image.getWidth() : 0; }
    public int getHeight() { return image != null ? image.getHeight() : 0; }
    public boolean isRunning() { return running; }
}
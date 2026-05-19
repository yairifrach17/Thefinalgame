import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class FallingObject {
    protected int x, y;
    protected int width, height;
    protected int speed;

    public FallingObject(int x, int y, int width, int height, int speed) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
    }

    // עדכון המיקום (נפילה למטה)
    public void update() {
        y += speed;
    }

    public abstract void draw(Graphics g);

    // החזרת מלבן החסימה להתנגשויות
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
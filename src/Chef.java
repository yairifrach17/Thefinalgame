import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Chef {
    private int x, y;
    private int speed;
    private int width, height;
    private BufferedImage image;
    private int screenWidth;

    public Chef(int startX, int startY, int screenWidth) {
        this.x = startX;
        this.y = startY;
        this.screenWidth = screenWidth;
        this.speed = 15; // מהירות תנועה חלקה

        // הגדרת גודל רצוי קטן ומתאים למשחק (80 על 80 פיקסלים)
        this.width = 80;
        this.height = 80;

        try {
            // טעינת התמונה המקורית
            BufferedImage originalImage = ImageIO.read(new File("Rsc/chef.png"));

            // יצירת תמונה חדשה מוקטנת בדיוק לגודל שקבענו (width, height)
            this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = this.image.createGraphics();
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();

        } catch (IOException e) {
            System.err.println("קובץ chef.png לא נמצא בתיקייה הראשית. נטען מלבן גיבוי.");
            this.image = null; // מוודא שאם יש שגיאה נצייר מלבן גיבוי בגודל החדש
        }
    }

    public void moveLeft() {
        x -= speed;
        if (x < 0) x = 0;
    }

    public void moveRight() {
        x += speed;
        if (x + width > screenWidth) x = screenWidth - width;
    }

    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, null);
        } else {
            // גיבוי צבעוני (למשל אדום) כדי שתראה אותו בבירור אם התמונה לא נטענת
            g.setColor(java.awt.Color.RED);
            g.fillRect(x, y, width, height);
        }
    }

    public Rectangle getBounds() {
        // מלבן ההתנגשות כעת מתאים בדיוק לגודל הקטן (80x80)
        return new Rectangle(x, y, width, height);
    }
}
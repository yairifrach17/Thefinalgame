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
    private int screenWidth; // גבול המסך לעדכון תנועה

    public Chef(int startX, int startY, int screenWidth) {
        this.x = startX;
        this.y = startY;
        this.screenWidth = screenWidth;
        this.speed = 10; // מהירות התזוזה בפיקסלים

        // טעינת תמונת השף
        try {
            // ודא שהתמונה נמצאת בתיקיית assets/ או src/ בהתאם למבנה הפרויקט שלך
            this.image = ImageIO.read(new File("assets/chef.png"));
            this.width = image.getWidth();
            this.height = image.getHeight();
        } catch (IOException e) {
            System.err.println("שגיאה בטעינת תמונת השף! נטען גודל ברירת מחדל.");
            e.printStackTrace();
            // ערכי ברירת מחדל במקרה שהתמונה לא נמצאה (כדי שהמשחק לא יקרוס)
            this.width = 64;
            this.height = 64;
        }
    }

    // תזוזה שמאלה - חסימה שלא ייצא מאפס
    public void moveLeft() {
        x -= speed;
        if (x < 0) {
            x = 0;
        }
    }

    // תזוזה ימינה - חסימה שלא ייצא מרוחב המסך פחות רוחב השחקן
    public void moveRight() {
        x += speed;
        if (x + width > screenWidth) {
            x = screenWidth - width;
        }
    }

    // מתודת הציור - נקראת מתוך ה-paintComponent של ה-JPanel
    public void draw(Graphics g) {
        if (image != null) {
            g.drawImage(image, x, y, null);
        } else {
            // גיבוי ויזואלי למקרה שהתמונה לא נטענה
            g.fillRect(x, y, width, height);
        }
    }

    // החזרת מלבן החסימה להתנגשויות
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // גטרים בסיסיים
    public int getX() { return x; }
    public int getY() { return y; }
}
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.Random;

public class ObjectManager {
    private final ArrayList<FallingObject1> objectsList;
    private final int panelWidth;
    private final int panelHeight;
    private final Random random;
    private BufferedImage goodImage;
    private BufferedImage badImage;

    public ObjectManager(int panelWidth, int panelHeight) {
        this.objectsList = new ArrayList<>();
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        this.random = new Random();
        loadImages();
    }

    private void loadImages() {
        try {
            BufferedImage origGood = ImageIO.read(new File("Rsc/good_ingredient.png"));
            BufferedImage origBad = ImageIO.read(new File("Rsc/bad_item.png"));

            // ---------------------------------------------------------
            // פה אתה קובע את הגודל של האובייקטים!
            // שנה את המספרים פה כדי להגדיל או להקטין את התמונות הנופלות
            // ---------------------------------------------------------
            int desiredWidth = 50;  // רוחב בפיקסלים
            int desiredHeight = 50; // גובה בפיקסלים

            goodImage = resizeImage(origGood, desiredWidth, desiredHeight);
            badImage = resizeImage(origBad, desiredWidth, desiredHeight);

        } catch (IOException e) {
            System.err.println("Error loading images: " + e.getMessage());
        }
    }

    // פונקציית עזר שמקטינה את התמונה באופן איכותי ברגע הטעינה
    private BufferedImage resizeImage(BufferedImage original, int width, int height) {
        if (original == null) return null;
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(original, 0, 0, width, height, null);
        g2d.dispose();
        return resized;
    }

    public void spawnObject(int currentScore) {
        int imgWidth = (goodImage != null) ? goodImage.getWidth() : 50;
        int xPos = random.nextInt(Math.max(1, panelWidth - imgWidth));

        int speed = 5;
        int baseDelay = 30;
        int difficultyDelayReduction = currentScore / 5;
        int finalDelay = Math.max(8, baseDelay - difficultyDelayReduction);

        int badItemChance = 15 + Math.min(55, currentScore / 3);
        int chanceResult = random.nextInt(100);

        FallingObject1 newObject;
        if (chanceResult < badItemChance) {
            newObject = new BadItem(xPos, speed, finalDelay, panelHeight, badImage);
        } else {
            newObject = new GoodIngredient(xPos, speed, finalDelay, panelHeight, goodImage);
        }

        synchronized (objectsList) {
            objectsList.add(newObject);
        }

        Thread objectThread = new Thread(newObject);
        objectThread.start();
    }

    public void removeFinishedObjects() {
        synchronized (objectsList) {
            objectsList.removeIf(obj -> obj.isRunning() == false);
        }
    }

    public void stopAllObjects() {
        synchronized (objectsList) {
            for (FallingObject1 obj : objectsList) {
                obj.stopFalling();
            }
            objectsList.clear();
        }
    }

    public ArrayList<FallingObject1> getObjectsList() {
        return objectsList;
    }
}
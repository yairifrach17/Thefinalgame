import java.util.ArrayList;

public class CollisionManager {

    // משתני עזר סטטיים (או שתוכל להעביר אובייקט GameState שמנהל אותם)
    public static int score = 0;
    public static int lives = 3;

    public static void checkCollisions(Chef chef, ArrayList<FallingObject> objectsList) {
        // רצים מהסוף להתחלה כדי שנוכל למחוק איברים מה-ArrayList בבטחה
        for (int i = objectsList.size() - 1; i >= 0; i--) {
            FallingObject currentObject = objectsList.get(i);

            // בדיקת התנגשות באמצעות intersects של מחלקת Rectangle
            if (chef.getBounds().intersects(currentObject.getBounds())) {

                // שימוש ב-instanceof כדי לזהות את סוג החפץ (פולימורפיזם)
                if (currentObject instanceof Ingredient) {
                    score += 10; // חפץ טוב - מוסיף ניקוד
                    System.out.println("התנגשות עם חפץ טוב! ניקוד: " + score);
                }
                else if (currentObject instanceof Obstacle) {
                    lives--; // חפץ רע - מוריד חיים
                    System.out.println("אאוץ'! התנגשות עם מכשול. חיים נותרו: " + lives);
                }

                // הסרת החפץ מהרשימה לאחר ההתנגשות
                objectsList.remove(i);
            }
        }
    }
}
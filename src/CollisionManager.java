import java.util.ArrayList;

public class CollisionManager {

    public static void checkCollisions(Chef chef, ArrayList<FallingObject> objectsList, GamePanel panel) {
        for (int i = objectsList.size() - 1; i >= 0; i--) {
            FallingObject currentObject = objectsList.get(i);

            // בדיקת התנגשות פיזית
            if (chef.getBounds().intersects(currentObject.getBounds())) {

                // בדיקה לפי שם המחלקה של החפץ הנופל
                String className = currentObject.getClass().getSimpleName();

                if (className.equals("Ingredient")) {
                    // הוספת 10 נקודות לחבר שלך
                    panel.setScore(panel.getScore() + 10);
                    System.out.println("התנגשות עם חפץ טוב! Score: " + panel.getScore());
                }
                else if (className.equals("Obstacle")) {
                    // הורדת חיים אחד לחבר שלך
                    panel.setLives(panel.getLives() - 1);
                    System.out.println("התנגשות עם מכשול! Lives: " + panel.getLives());
                }

                // הסרת החפץ שהתנגשנו בו
                objectsList.remove(i);
            }
        }
    }
}
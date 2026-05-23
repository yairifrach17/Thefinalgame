import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

    // התוספת שלנו: משתנה שישמור את הטקסט של הניקוד
    private JLabel scoreLabel;

    public GameOverPanel(GameFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 228, 225)); // אדמדם בהיר

        JLabel title = new JLabel("פסילה!");
        title.setFont(new Font("Arial", Font.BOLD, 54));
        title.setForeground(Color.RED);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- הוספנו את עיצוב הניקוד ---
        scoreLabel = new JLabel("הניקוד שלך: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 32));
        scoreLabel.setForeground(Color.BLUE);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton menuButton = new JButton("חזור לתפריט הראשי");
        menuButton.setFont(new Font("Arial", Font.BOLD, 24));
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // לחיצה על הכפתור תחזיר אותנו למסך התפריט
        menuButton.addActionListener(e -> frame.showMenuScreen());

        // --- הוספנו את עיצוב כפתור היציאה ---
        JButton quitButton = new JButton("יציאה מהמשחק");
        quitButton.setFont(new Font("Arial", Font.BOLD, 24));
        quitButton.setBackground(Color.RED);
        quitButton.setForeground(Color.WHITE);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        quitButton.addActionListener(e -> System.exit(0)); // סוגר את המשחק לגמרי

        // --- סידור המסך: שמרתי על הרווחים המקוריים שלכם בדיוק! ---
        add(Box.createVerticalStrut(200)); // הרווח המקורי שלכם
        add(title);

        add(Box.createVerticalStrut(40)); // קצת רווח כדי להכניס את הניקוד מתחת לכותרת
        add(scoreLabel);

        add(Box.createVerticalStrut(60)); // הרווח המקורי שלכם
        add(menuButton);

        add(Box.createVerticalStrut(20)); // קצת רווח כדי להכניס את הכפתור החדש
        add(quitButton);
    }

    // --- הפונקציה שמעדכנת את הטקסט ברגע הפסילה ---
    public void setFinalScore(int score) {
        scoreLabel.setText("כל הכבוד! הצלחת לצבור: " + score + " נקודות!");
    }
}
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MenuPanel extends JPanel {

    public MenuPanel(GameFrame frame) {
        // הגדרת פריסת הרכיבים (אחד מתחת לשני) וצבע רקע
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255)); // תכלת בהיר

        ImageIcon titleImage = new ImageIcon("Rsc/Gotta.png");

// 2. יצירת ה-JLabel כאשר הוא מקבל את התמונה במקום טקסט
        JLabel title = new JLabel(titleImage);

// 3. מרכוז הרכיב (חובה עבור BoxLayout שבו השתמשנו)
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- אזור טקסט להוראות ---
        JTextArea instructions = new JTextArea(
                "Gotta catch them all \n\n" +
                        "המטרה: לאסוף מצרכים ולשרוד כמה שיותר.\n" +
                        "תנועה: חיצי המקלדת."
        );
        instructions.setFont(new Font("Arial", Font.PLAIN, 24));
        instructions.setEditable(false);
        instructions.setOpaque(false); // שקוף כדי שיראו את צבע הרקע של הפאנל
        instructions.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions.setMargin(new Insets(20, 20, 20, 20));

        // --- כפתור התחלה ---
        JButton startButton = new JButton("התחל משחק");
        startButton.setFont(new Font("Arial", Font.BOLD, 28));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // לחיצה על הכפתור תפעיל את הפונקציה startGame ב-GameFrame
        startButton.addActionListener(e -> frame.startGame());

        // --- הוספת כל הרכיבים לפאנל עם רווחים ביניהם ---
        add(Box.createVerticalStrut(100)); // רווח מלמעלה
        add(title);
        add(Box.createVerticalStrut(40));  // רווח בין הכותרת להוראות
        add(instructions);
        add(Box.createVerticalStrut(50));  // רווח בין ההוראות לכפתור
        add(startButton);
    }

}
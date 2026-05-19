import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(GameFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255)); // תכלת בהיר

        JLabel title = new JLabel("בהלה במטבח");
        title.setFont(new Font("Arial", Font.BOLD, 64));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea instructions = new JTextArea(
                "ברוכים הבאים ל'בהלה במטבח'!\n\n" +
                        "המטרה: לאסוף מצרכים ולשרוד כמה שיותר.\n" +
                        "תנועה: חיצי המקלדת."
        );
        instructions.setFont(new Font("Arial", Font.PLAIN, 24));
        instructions.setEditable(false);
        instructions.setOpaque(false);
        instructions.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructions.setMargin(new Insets(20, 20, 20, 20));

        JButton startButton = new JButton("התחל משחק");
        startButton.setFont(new Font("Arial", Font.BOLD, 28));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // לחיצה על הכפתור תפעיל את המשחק ב-GameFrame
        startButton.addActionListener(e -> frame.startGame());

        add(Box.createVerticalStrut(100));
        add(title);
        add(Box.createVerticalStrut(40));
        add(instructions);
        add(Box.createVerticalStrut(50));
        add(startButton);
    }
}
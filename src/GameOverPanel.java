import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

    public GameOverPanel(GameFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 228, 225)); // אדמדם בהיר

        JLabel title = new JLabel("פסילה!");
        title.setFont(new Font("Arial", Font.BOLD, 54));
        title.setForeground(Color.RED);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton menuButton = new JButton("חזור לתפריט הראשי");
        menuButton.setFont(new Font("Arial", Font.BOLD, 24));
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // לחיצה על הכפתור תחזיר אותנו למסך התפריט
        menuButton.addActionListener(e -> frame.showMenuScreen());

        add(Box.createVerticalStrut(200));
        add(title);
        add(Box.createVerticalStrut(60));
        add(menuButton);
    }
}
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class MenuPanel extends JPanel {

    public MenuPanel(GameFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255));

        add(Box.createVerticalGlue());

        // --- טעינת התמונה של הכותרת ---
        String imagePath = "Rsc/Gotta.png";
        File imageCheck = new File(imagePath);

        if (imageCheck.exists()) {
            JLabel title = new JLabel(new ImageIcon(imagePath));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(title);
        } else {
            // אם התמונה לא קיימת בנתיב, נודיע על כך בבירור!
            JLabel errorTitle = new JLabel("שגיאה: התמונה Gotta.png לא נמצאה בנתיב");
            errorTitle.setFont(new Font("Arial", Font.BOLD, 30));
            errorTitle.setForeground(Color.RED);
            errorTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(errorTitle);
        }

        add(Box.createVerticalStrut(60));

        // פאנל כפתורים
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        Font buttonFont = new Font("Arial", Font.BOLD, 24);
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        JButton startButton = new JButton("התחל משחק");
        startButton.setFont(buttonFont);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setCursor(handCursor);
        startButton.addActionListener(e -> frame.startGame());
        buttonPanel.add(startButton);

        buttonPanel.add(Box.createVerticalStrut(20));

        JButton helpButton = new JButton("הוראות (How to play)");
        helpButton.setFont(buttonFont);
        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        helpButton.setCursor(handCursor);
        helpButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "המטרה: לאסוף מצרכים ולשרוד.\nתנועה: חיצי המקלדת.", "הוראות", JOptionPane.INFORMATION_MESSAGE);
        });
        buttonPanel.add(helpButton);

        buttonPanel.add(Box.createVerticalStrut(20));

        JButton quitButton = new JButton("יציאה (Quit)");
        quitButton.setFont(buttonFont);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setCursor(handCursor);
        quitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(quitButton);

        add(buttonPanel);
        add(Box.createVerticalGlue());
    }
}
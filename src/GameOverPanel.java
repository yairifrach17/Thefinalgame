import javax.swing.*;
import java.awt.*;
import java.io.File; // 🔴 תוספת: חובה כדי לבדוק אם קובץ התמונה קיים

public class GameOverPanel extends JPanel {

    private JLabel scoreLabel;
    private Image backgroundImage;

    public GameOverPanel(GameFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(255, 228, 225));

        // 🔴 תוספת 2: טעינת תמונת הרקע מתוך תיקיית Rsc
        String bgPath = "Rsc/gameover_bg.png";
        if (new File(bgPath).exists()) {
            backgroundImage = new ImageIcon(bgPath).getImage();
            System.out.println("✅ הצלחה: רקע מסך הפסילה נטען!");
        } else {
            System.out.println("⚠️ שים לב: gameover_bg.png לא נמצא בתיקיית Rsc (מוצג רקע גיבוי).");
        }

        JLabel title = new JLabel("פסילה!");
        title.setFont(new Font("Arial", Font.BOLD, 54));
        title.setForeground(Color.RED);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreLabel = new JLabel("הניקוד שלך: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 32));
        scoreLabel.setForeground(Color.CYAN);
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton menuButton = new JButton("חזור לתפריט הראשי");
        menuButton.setFont(new Font("Arial", Font.BOLD, 24));
        menuButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuButton.addActionListener(e -> frame.showMenuScreen());

        JButton quitButton = new JButton("יציאה מהמשחק");
        quitButton.setFont(new Font("Arial", Font.BOLD, 24));
        quitButton.setBackground(Color.RED);
        quitButton.setForeground(Color.WHITE);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        quitButton.addActionListener(e -> System.exit(0));

        add(Box.createVerticalStrut(200));
        add(title);

        add(Box.createVerticalStrut(40));
        add(scoreLabel);

        add(Box.createVerticalStrut(60));
        add(menuButton);

        add(Box.createVerticalStrut(20));
        add(quitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void setFinalScore(int score) {
        if (score < 100) {
            scoreLabel.setText("חחח יאפססס הצלחת לצבור רק " + score + " נקודות!");
        } else {
            scoreLabel.setText("כל הכבוד! הצלחת לצבור: " + score + " נקודות!");
        }
    }
}
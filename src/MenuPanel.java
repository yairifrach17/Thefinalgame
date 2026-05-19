import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.sound.sampled.*;

public class MenuPanel extends JPanel {

    private Image bgImage; // משתנה לתמונת הרקע
    private Clip musicClip; // משתנה למוזיקה

    public MenuPanel(GameFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 248, 255)); // צבע גיבוי אם אין תמונת רקע

        // 1. טעינת תמונת הרקע (Background Image)
        String bgPath = "Rsc/bg.png";
        if (new File(bgPath).exists()) {
            bgImage = new ImageIcon(bgPath).getImage();
        }

        // 2. הפעלת מוזיקת הרקע (Background Music)
        playMusic("Rsc/theme.wav");

        add(Box.createVerticalGlue());

        // 3. אנימציה לכותרת (Title Animation)
        String imagePath = "Rsc/Gotta.png";
        if (new File(imagePath).exists()) {
            // שימוש במחלקה הפנימית המיוחדת שיצרנו למטה בשביל הריחוף
            FloatingTitle title = new FloatingTitle(new ImageIcon(imagePath));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(title);
        } else {
            JLabel errorTitle = new JLabel("שגיאה: התמונה Gotta.png לא נמצאה");
            errorTitle.setFont(new Font("Arial", Font.BOLD, 30));
            errorTitle.setForeground(Color.RED);
            errorTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(errorTitle);
        }

        add(Box.createVerticalStrut(60));

        // פאנל כפתורים (שקוף כדי שיראו את הרקע)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        // 4. כפתורים מעוצבים (Custom Buttons & Hover)
// --- פאנל כפתורים (בתוך MenuPanel.java) ---
        JButton startButton = new JButton("התחל משחק (Start Game)");
        styleButton(startButton);
        startButton.addActionListener(e -> {
            stopMusic();
            frame.startGame();
        });
        buttonPanel.add(startButton);

        // רווח בין כפתור 1 לכפתור 2
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton helpButton = new JButton("הוראות (How to play)");
        styleButton(helpButton);
        helpButton.addActionListener(e -> frame.showHelpScreen());
        buttonPanel.add(helpButton);

        // --- התיקון: הוספת רווח חסר בין כפתור 2 לכפתור 3 ---
        buttonPanel.add(Box.createVerticalStrut(20));

        JButton quitButton = new JButton("יציאה (Quit)");
        styleButton(quitButton);
        quitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(quitButton);

        add(buttonPanel);
        add(Box.createVerticalGlue());
    }

    /**
     * פונקציה שעושה Overriding כדי לצייר את תמונת הרקע על כל המסך
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * פונקציית עזר לעיצוב הכפתורים (צבעים, חוסר מסגרת, ואפקט מעבר עכבר)
     */
    private void styleButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 24));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(70, 130, 180)); // צבע כחול-פלדה
        btn.setFocusPainted(false); // מעלים את הריבוע המכוער של הפוקוס
        btn.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // הוספת אפקט Hover (שינוי צבע כשהעכבר עליו)
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(100, 149, 237)); // כחול בהיר יותר
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(70, 130, 180)); // חוזר לצבע המקורי
            }
        });
    }

    /**
     * פונקציה לטעינה וניגון של מוזיקת הרקע בלולאה
     */
    private void playMusic(String filePath) {
        try {
            File musicFile = new File(filePath);
            if (musicFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                musicClip = AudioSystem.getClip();
                musicClip.open(audioInput);
                musicClip.loop(Clip.LOOP_CONTINUOUSLY); // מנגן בלולאה אינסופית
            } else {
                System.out.println("קובץ המוזיקה לא נמצא: " + filePath);
            }
        } catch (Exception e) {
            System.out.println("שגיאה בטעינת המוזיקה.");
        }
    }

    /**
     * פונקציה לעצירת המוזיקה
     */
    private void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }

    /**
     * מחלקה פנימית שיוצרת את אפקט האנימציה - הכותרת תרחף למעלה ולמטה
     */
    private class FloatingTitle extends JLabel {
        private double angle = 0;
        private final int AMPLITUDE = 15; // כמה פיקסלים הכותרת עולה ויורדת

        public FloatingTitle(ImageIcon icon) {
            super(icon);
            // טיימר שרץ כל 50 מילי-שניות ומעדכן את זווית הריחוף
            Timer timer = new Timer(50, e -> {
                angle += 0.15;
                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            // חישוב המיקום החדש בעזרת פונקציית סינוס לתנועה חלקה
            int yOffset = (int) (Math.sin(angle) * AMPLITUDE);

            // מזיזים את המכחול של ג'אבה, מציירים, ומחזירים חזרה
            g2d.translate(0, yOffset);
            super.paintComponent(g);
            g2d.translate(0, -yOffset);
        }
    }
}
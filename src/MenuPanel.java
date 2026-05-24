import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.sound.sampled.*;

public class MenuPanel extends JPanel {

    private Image bgImage;
    private Clip musicClip;

    public MenuPanel(GameFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String bgPath = "Rsc/bg.png";
        if (new File(bgPath).exists()) {
            bgImage = new ImageIcon(bgPath).getImage();
        } else {
            setBackground(new Color(240, 248, 255));
            System.out.println("התרעה: תמונת הרקע bg.png לא נמצאה בתיקיית Rsc");
        }

        playMusic("Rsc/theme.wav");

        add(Box.createVerticalGlue());

        String imagePath = "Rsc/Gotta.png";
        if (new File(imagePath).exists()) {
            FloatingTitle title = new FloatingTitle(new ImageIcon(imagePath));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(title);
        } else {

            JLabel errorTitle = new JLabel("PokePanic");
            errorTitle.setFont(new Font("Arial", Font.BOLD, 54));
            errorTitle.setForeground(new Color(255, 215, 0));
            errorTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(errorTitle);
        }

        add(Box.createVerticalStrut(60));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton startButton = new JButton("התחל משחק (Start Game)");
        styleButton(startButton);
        startButton.addActionListener(e -> {
            stopMusic();
            frame.startGame();
        });
        buttonPanel.add(startButton);

        buttonPanel.add(Box.createVerticalStrut(20));

        JButton helpButton = new JButton("הוראות (How to play)");
        styleButton(helpButton);
        helpButton.addActionListener(e -> frame.showHelpScreen());
        buttonPanel.add(helpButton);

        buttonPanel.add(Box.createVerticalStrut(20));

        JButton quitButton = new JButton("יציאה (Quit)");
        styleButton(quitButton);
        quitButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(quitButton);

        add(buttonPanel);

        add(Box.createVerticalGlue());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.BOLD, 24));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(70, 130, 180));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(100, 149, 237));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(70, 130, 180));
            }
        });
    }

    /**
     * פונקציה לטעינה והפעלת קובץ האודיו ברקע
     */
    private void playMusic(String filePath) {
        try {
            File musicFile = new File(filePath);
            if (musicFile.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                musicClip = AudioSystem.getClip();
                musicClip.open(audioInput);
                musicClip.loop(Clip.LOOP_CONTINUOUSLY); // הפעלה חוזרת אינסופית
            } else {
                System.out.println("התרעה: קובץ הסאונד theme.wav לא נמצא בתיקיית Rsc");
            }
        } catch (Exception e) {
            System.out.println("שגיאה בטעינת המוזיקה: " + e.getMessage());
        }
    }

    /**
     * פונקציית עזר לעצירת המוזיקה במעבר למסכים אחרים
     */
    private void stopMusic() {
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
    }

    /**
     * תת-מחלקה פנימית שמייצרת את האפקט המרחף והעדין ללוגו של המשחק
     */
    private class FloatingTitle extends JLabel {
        private double angle = 0;

        public FloatingTitle(ImageIcon icon) {
            super(icon);
            // טיימר שמריץ עדכון ציור קטן כל 50 מילישניות
            Timer timer = new Timer(50, e -> {
                angle += 0.12;
                repaint();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            // חישוב מתמטי בעזרת סינוס כדי ליצור תנועה חלקה למעלה ולמטה
            int yOffset = (int) (Math.sin(angle) * 10);
            g2d.translate(0, yOffset);
            super.paintComponent(g);
            g2d.translate(0, -yOffset); // איפוס המטריצה לאחר הציור
        }
    }
}
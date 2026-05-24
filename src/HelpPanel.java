import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class HelpPanel extends JPanel {

    private Image bgImage;

    public HelpPanel(GameFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String bgPath = "Rsc/bg.png";
        if (new File(bgPath).exists()) {
            bgImage = new ImageIcon(bgPath).getImage();
        } else {
            setBackground(new Color(240, 248, 255)); // גיבוי תכלת
        }

        add(Box.createVerticalGlue());

        JLabel titleLabel = new JLabel("איך משחקים?");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(255, 215, 0)); // צבע צהוב פוקימון
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);

        add(Box.createVerticalStrut(40));


        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        addInstructionLine(textPanel, "המטרה: לתפוס כמה שיותר פיקאצ'ויים הנופלים מהשמיים");
        addInstructionLine(textPanel, "זהירות: הימנעו מלהתנגש במייאויים שפוסלים אתכם");
        addInstructionLine(textPanel, "תנועה: השתמשו בחיצי המקלדת או במקשים A, D כדי להזיז את הפוקדור");

        add(textPanel);

        add(Box.createVerticalStrut(50)); // רווח לפני הכפתור

        JButton backButton = new JButton("חזור לתפריט");
        styleButton(backButton);
        backButton.addActionListener(e -> frame.showMenuScreen()); // מחזיר לתפריט הראשי
        add(backButton);

        add(Box.createVerticalGlue());
    }

    private void addInstructionLine(JPanel panel, String text) {
        JLabel line = new JLabel(text);
        line.setFont(new Font("Arial", Font.BOLD, 22));

        line.setForeground(Color.BLACK);

        line.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(line);
        panel.add(Box.createVerticalStrut(15));
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

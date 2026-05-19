import javax.swing.*;
import java.awt.*;

/**
 * רכיב JLabel מותאם אישית שמצייר קו מתאר מסביב לאותיות.
 */
public class OutlinedLabel extends JLabel {
    private Color outlineColor;
    private int outlineSize;

    public OutlinedLabel(String text, Color textColor, Color outlineColor, int outlineSize) {
        super(text);
        this.outlineColor = outlineColor;
        this.outlineSize = outlineSize;
        setForeground(textColor); // צבע האותיות הפנימי
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // החלקת קצוות (Antialiasing) לטקסט ולמסגרת
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        FontMetrics fm = g2.getFontMetrics(getFont());
        int x = 0;

        if (getHorizontalAlignment() == SwingConstants.CENTER) {
            x = (getWidth() - fm.stringWidth(getText())) / 2;
        } else if (getHorizontalAlignment() == SwingConstants.RIGHT) {
            x = getWidth() - fm.stringWidth(getText());
        }

        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        // ציור המסגרת
        g2.setColor(outlineColor);
        for (int i = -outlineSize; i <= outlineSize; i++) {
            for (int j = -outlineSize; j <= outlineSize; j++) {
                if (i != 0 || j != 0) {
                    g2.drawString(getText(), x + i, y + j);
                }
            }
        }

        // ציור הטקסט הפנימי
        g2.setColor(getForeground());
        g2.drawString(getText(), x, y);

        g2.dispose();
    }
}
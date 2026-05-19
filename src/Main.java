import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // מפעיל את יצירת החלון הראשי בתהליכון המיועד לממשק משתמש
        SwingUtilities.invokeLater(() -> {
            GameFrame frame = new GameFrame();
        });
    }
}

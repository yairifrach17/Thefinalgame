import javax.swing.JFrame;

public class GameFrame extends JFrame {

    public GameFrame() {
        this.setTitle("My Game"); // אפשר לשנות לשם המשחק שלכם
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600); // גודל החלון, אפשר להתאים

        // יוצר את הפאנל שכתבת ומוסיף אותו לחלון
        GamePanel panel = new GamePanel(this);
        this.add(panel);

        this.setLocationRelativeTo(null); // ממורכז במסך
        this.setVisible(true);

        // מתחיל את התהליכון (Thread) של המשחק
        panel.startGame();
    }

    // הפונקציה שהפאנל קורא לה כשהחיים נגמרים
    public void showGameOverScreen() {
        System.out.println("Game Over!");
        // חברי הצוות יוסיפו כאן בהמשך את המסך המעוצב של הסיום
    }
}
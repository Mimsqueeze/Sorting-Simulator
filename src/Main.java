import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                startScreen startScreen = new startScreen();
                Graphics2D g = new Graphics2D();
                startScreen.render();
            }
        });
    }
}

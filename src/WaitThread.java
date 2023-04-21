import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WaitThread extends Thread {
    Main main;
    private boolean next = false;
    public WaitThread(Main main) {
        this.main = main;
    }
    @Override
    public void run() {
        main.panel.setFocusable(true);
        main.panel.requestFocusInWindow();
        main.panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    next = true;
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("a");
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    next = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    next = true;
                }
            }
            
        });   
    }
}

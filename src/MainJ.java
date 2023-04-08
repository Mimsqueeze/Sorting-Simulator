import javax.swing.*;
public class MainJ {
    JFrame frame;
    myPanel panel;
    MainJ() {
        frame = new JFrame("Sorting Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new myPanel();
        panel.setPreferredSize(new Dimension(600, 600));
        frame.pack();
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
        panel.paintComponent(panel.getGraphics());
    }
    private static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        MainJ drive = new MainJ();
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runGUI();
            }
        });
    }
    class myPanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.RED);
            g.fillRect(0,0,500,500);
        }
    }
}

import java.awt.*;

import javax.swing.*;

public class MainA {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runGUI();
            }
        });
    }
    String[] sortingAlgs = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort", "Merge Sort", "Heap Sort"};
    JFrame frame;
    myPanel panel;
    JComboBox<String> dropDown;
    JButton run = new JButton("Simulate");
    MainA() {
        frame = new JFrame("Sorting Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new myPanel();
        panel.setLayout(new GridLayout(20, 2));
        panel.setPreferredSize(new Dimension(1000, 600));
        dropDown = new JComboBox<>(sortingAlgs);
        frame.setContentPane(panel);
        panel.add(new JLabel("Sorting Algorithm Simulator"));
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(new JLabel("Sorting Algorithm"));
        panel.add(dropDown);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(new JLabel("Enter Size"));
        panel.add(new JTextField());
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(run);
        frame.pack();
        frame.setVisible(true);
        panel.paintComponent(panel.getGraphics());
    }
    private static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        MainA drive = new MainA();

    }
     class myPanel extends JPanel {
         public void paintComponent(Graphics g) {
             super.paintComponent(g);
             //g.setColor(Color.RED);
             //g.fillRect(0,0,500,500);
         }
     }

}

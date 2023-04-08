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
    JPanel panel;
    JComboBox<String> dropDown;
    JButton run = new JButton("Start Simulation");
    Font myFont = new Font("Courier New", Font.BOLD, 25);
    Font titleFont = new Font("Courier New", Font.BOLD, 30);
    MainA() {
        frame = new JFrame("Sorting Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.black);
        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(new GridLayout(20, 2));
        panel.setPreferredSize(new Dimension(1000, 600));
        frame.setContentPane(panel);

        JLabel title = new JLabel("Sorting Algorithm Simulator");
        title.setFont(titleFont);
        title.setForeground(Color.green);
        panel.add(title);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel algorithmText = new JLabel("Sorting Algorithm");
        algorithmText.setFont(myFont);
        algorithmText.setForeground(Color.green);
        panel.add(algorithmText);

        dropDown = new JComboBox<>(sortingAlgs);
        dropDown.setFont(myFont);
        //dropDown.setBackground(Color.gray);
        //dropDown.setForeground(Color.green);
        panel.add(dropDown);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        JLabel sizeText = new JLabel("Enter Size");
        sizeText.setFont(myFont);
        sizeText.setForeground(Color.green);
        panel.add(sizeText);

        JTextField sizeInput = new JTextField();
        sizeInput.setFont(myFont);
        //sizeInput.setBackground(Color.gray);
        //sizeInput.setForeground(Color.green);
        panel.add(sizeInput);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        run.setFont(myFont);
        //run.setBackground(Color.lightGray);
        //run.setForeground(Color.green);
        panel.add(run);

        frame.pack();
        frame.setVisible(true);
    }
    private static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        MainA drive = new MainA();
    }
}

//package src;

import javax.swing.*;
import java.awt.*;

public class MainJ {
    String[] sortingAlgs = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort", "Merge Sort", "Heap Sort"};
    JFrame frame;
    JPanel panel;
    JComboBox<String> dropDown;
    JTextField sizeInput = new JTextField();
    JButton run = new JButton("Simulate");
    Font myFont = new Font("Courier New", Font.BOLD, 25);
    Font titleFont = new Font("Courier New", Font.BOLD, 30);
    screen s;
    JLabel title;
    JLabel algorithmText;
    JLabel sizeText;
    MainJ() {
        frame = new JFrame("Sorting Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(new GridLayout(20, 2));
        panel.setPreferredSize(new Dimension(1000, 600));
        frame.setContentPane(panel);

        title = new JLabel("Sorting Algorithm Simulator");
        title.setFont(titleFont);
        title.setForeground(Color.green);
        panel.add(title);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        algorithmText = new JLabel("Sorting Algorithm");
        algorithmText.setFont(myFont);
        algorithmText.setForeground(Color.green);
        panel.add(algorithmText);

        dropDown = new JComboBox<>(sortingAlgs);
        dropDown.setFont(myFont);
        panel.add(dropDown);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        sizeText = new JLabel("Enter Size");
        sizeText.setFont(myFont);
        sizeText.setForeground(Color.green);
        panel.add(sizeText);

        sizeInput.setFont(myFont);
        panel.add(sizeInput);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        run.setFont(myFont);
        panel.add(run);

        run.addActionListener(e -> onClick());
        frame.pack();
        frame.setVisible(true);

    }

    private void onClick() {
        String size = sizeInput.getText();
        Object algorithm = dropDown.getSelectedItem();
        panel.removeAll();
        sort.runSimulation(Integer.parseInt(size),(String) algorithm);
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
    // if inspecting = true, pointers are for inspecting the element
    // if false, pointers are for swapping
    public void updateUI(int[] array, int[] pointers, int size, boolean inspecting) {
        s.setArrays(array,pointers,size);
        s.render((Graphics2D) panel.getGraphics());
    }
}

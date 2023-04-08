import java.awt.*;
import javax.swing.*;

public class startScreen{
    String[] sortingAlgs = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort", "Merge Sort", "Heap Sort"};
    JFrame frame;
    JPanel panel;
    JComboBox<String> dropDown;
    JTextField sizeInput;
    JButton run = new JButton("Simulate");
    public void render(Graphics2D g)  {
        frame = new JFrame("Sorting Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
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
        sizeInput = new JTextField();
        panel.add(sizeInput);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(run);
        run.addActionListener(e -> onClick());
        frame.pack();
        frame.setVisible(true);
    }
    public void onClick() {
        String size = sizeInput.getText();
        Object[] algorithm = dropDown.getSelectedObjects();
        //setScreen()
        //within setscreen,
    }
}

import java.awt.*;
import javax.swing.*;

public class startScreen{
    String[] sortingAlgs = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort", "Merge Sort", "Heap Sort"};
    JFrame frame;
    JPanel panel;
    JComboBox<String> dropDown;
    JTextField sizeInput;
    JButton run = new JButton("Simulate");
    Font myFont = new Font("Courier New", Font.BOLD, 25);
    Font titleFont = new Font("Courier New", Font.BOLD, 30);
    public void render(Graphics2D g)  {
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
        panel.add(dropDown);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));
       
        JLabel sizeText = new JLabel("Enter Size");
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
    public void onClick() {
        String size = sizeInput.getText();
        Object[] algorithm = dropDown.getSelectedObjects();
        //setScreen()
        //within setscreen,
    }
}

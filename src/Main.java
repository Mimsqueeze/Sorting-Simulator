// package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class Main {
    String[] sortingAlgs = {"Bubble Sort", "Selection Sort", "Insertion Sort", "Quick Sort", "Merge Sort", "Heap Sort", "Intro Sort", "Bogo Sort"};
    JFrame frame;
    JPanel panel;
    JComboBox<String> dropDown;
    JTextField sizeInput = new JTextField();
    JTextField simInput = new JTextField();
    JCheckBox everyCheckBox = new JCheckBox();
    JCheckBox soundCheckBox = new JCheckBox();
    JButton run;
    JButton exit;
    Font myFont = new Font("Courier New", Font.BOLD, 25);
    Font titleFont = new Font("Courier New", Font.BOLD, 35);
    graphScreen s;
    JLabel title;
    JLabel algorithmText;
    JLabel sizeText;
    JLabel simText;
    JLabel everyText;
    JLabel soundText;
    boolean soundOn;
    Main() {
        frame = new JFrame("Sorting Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setBackground(Color.black);
        start();
    }
    public void start() {
        panel.removeAll();
        panel.setLayout(new GridLayout(20, 2));
        panel.setPreferredSize(new Dimension(graphScreen.WIDTH, graphScreen.HEIGHT));
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

        sizeText = new JLabel("Enter Size (1 - 1200)");
        sizeText.setFont(myFont);
        sizeText.setForeground(Color.green);
        panel.add(sizeText);

        sizeInput.setFont(myFont);
        // sizeInput.setText("100");
        panel.add(sizeInput);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        simText = new JLabel("Enter Number of Simulations (1 - 10000)");
        simText.setFont(myFont);
        simText.setForeground(Color.green);
        panel.add(simText);

        simInput.setFont(myFont);
        // simInput.setText("1");
        panel.add(simInput);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        everyText = new JLabel("Show Every Simulation");
        everyText.setFont(myFont);
        everyText.setForeground(Color.green);
        panel.add(everyText);
        panel.add(everyCheckBox);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        soundText = new JLabel("Sound (Slows Processing Significantly)");
        soundText.setFont(myFont);
        soundText.setForeground(Color.green);
        panel.add(soundText);
        panel.add(soundCheckBox);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        run = new JButton("Start Simulation");
        run.setFont(myFont);
        panel.add(run);

        // panel.add(Box.createRigidArea(new Dimension(10, 0)));
        exit = new JButton("Exit");
        exit.setFont(myFont);
        panel.add(exit);

        run.addActionListener(e -> runClick());
        exit.addActionListener(e -> exitClick());
        frame.pack();
        frame.setVisible(true);
    }
    private void exitClick() {
        System.exit(0);
    }
    private void runClick() {
        panel.removeAll();
        panel.getGraphics().setColor(Color.BLACK);
        panel.getGraphics().fillRect(0, 0, graphScreen.WIDTH, graphScreen.HEIGHT);
        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point2D click = e.getPoint();
                s.onClick((int) click.getX(), (int) click.getY());
            }
        });
        String size = sizeInput.getText();
        Object algorithm = dropDown.getSelectedItem();
        String numSims = simInput.getText();
        Sim newSimulation = new Sim();
        boolean show = everyCheckBox.isSelected();
        this.soundOn = soundCheckBox.isSelected();
        newSimulation.runSimulation(this, Integer.parseInt(size),(String) algorithm, Integer.parseInt(numSims), show);
    }

    private static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        Main drive = new Main();
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
    // data[0] is num comparisons, data[1] is num swaps
    public void updateUI(int[] array, int[] pointers, int size, boolean inspecting, int[] data) {
        s = new graphScreen(this);
        if (pointers != null) {
            s.setArrays(array, pointers, size, inspecting);
        } else {
            s.setArray(array, size);
        }

        if (data != null) {
            s.setBox(data);
        }
        s.render((Graphics2D) panel.getGraphics());
    }
    public void finish(int[] array, int size, int[] data, int numSims, long time) {
        s = new graphScreen(this);
        s.setArray(array, size);
        s.setBox(data);
        s.setFinish(numSims, time);
        s.render((Graphics2D) panel.getGraphics());
    }
}

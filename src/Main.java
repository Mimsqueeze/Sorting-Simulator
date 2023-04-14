import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class Main {

    /* Array for all the sorting algorithms our program can simulate (also is 
     * used for the dropdown menu to select the sorting algorithm)
     */
    public final String[] sortingAlgs = 
    {
        "Bubble Sort", "Selection Sort", "Insertion Sort", 
        "Quick Sort", "Merge Sort", "Heap Sort", 
        "Intro Sort", "Bogo Sort"
    };
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
    GraphScreen graphScreen;
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
        panel.setPreferredSize(new Dimension(GraphScreen.WIDTH, GraphScreen.HEIGHT));
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
        panel.add(sizeInput);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        simText = new JLabel("Enter Number of Simulations (1 - 10000)");
        simText.setFont(myFont);
        simText.setForeground(Color.green);
        panel.add(simText);

        simInput.setFont(myFont);
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
        panel.getGraphics().fillRect(0, 0, GraphScreen.WIDTH, GraphScreen.HEIGHT);
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point2D click = e.getPoint();
                graphScreen.onClick((int) click.getX(), (int) click.getY());
            }
        });
        int size = Integer.parseInt(sizeInput.getText());
        String algorithm = (String) dropDown.getSelectedItem();
        int numSims = Integer.parseInt(simInput.getText());
        boolean showSimulations = everyCheckBox.isSelected();
        this.soundOn = soundCheckBox.isSelected();

        // Create a new Graph Screen to be displayed
        graphScreen = new GraphScreen(this, (Graphics2D) panel.getGraphics(), size);

        // Create a new Simulation to be ran
        Sim newSimulation = new Sim(this, graphScreen, size, algorithm, numSims);
        newSimulation.runSimulation();
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
}

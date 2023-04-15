import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class Main {

    /* Array for all the sorting algorithms our program can simulate (also is 
     * used for the dropdown menu to select the sorting algorithm) */
    public final String[] sortingAlgs = {
        Constants.SORTING_ALG_NAMES.BUBBLE,
        Constants.SORTING_ALG_NAMES.SELECTION,
        Constants.SORTING_ALG_NAMES.INSERTION,
        Constants.SORTING_ALG_NAMES.QUICK,
        Constants.SORTING_ALG_NAMES.MERGE,
        Constants.SORTING_ALG_NAMES.HEAP,
        Constants.SORTING_ALG_NAMES.INTRO,
        Constants.SORTING_ALG_NAMES.BOZO,
        Constants.SORTING_ALG_NAMES.CUSTOM
    };
    JFrame frame;
    JPanel panel;
    JComboBox<String> dropDown;
    JTextField sizeInput = new JTextField();
    JTextField simInput = new JTextField();
    JCheckBox everyCheckBox = new JCheckBox();
    JCheckBox soundCheckBox = new JCheckBox();
    JCheckBox customCheckBox = new JCheckBox();
    JCheckBox waitCheckBox = new JCheckBox();
    JButton run;
    JButton exit;
    Font myFont = new Font("Courier New", Font.BOLD, 25);
    Font titleFont = new Font("Courier New", Font.BOLD, 35);
    GraphScreen graphScreen;
    JLabel title;
    JLabel algorithmText;
    JLabel sizeText;
    JLabel simText; 
    JLabel customText;
    JLabel everyText;
    JLabel soundText;
    JLabel waitText;
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
        panel.setLayout(new GridLayout(25, 1));
        panel.setPreferredSize(new Dimension(Constants.SCREEN_SIZES.WIDTH, Constants.SCREEN_SIZES.HEIGHT));
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

        simText = new JLabel("Enter Number of Simulations");
        simText.setFont(myFont);
        simText.setForeground(Color.green);
        panel.add(simText);

        simInput.setFont(myFont);
        panel.add(simInput);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        customText = new JLabel("Use Custom Dataset");
        customText.setFont(myFont);
        customText.setForeground(Color.green);
        panel.add(customText);
        panel.add(customCheckBox);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        waitText = new JLabel("Wait For Click To Continue");
        waitText.setFont(myFont);
        waitText.setForeground(Color.green);
        panel.add(waitText);
        panel.add(waitCheckBox);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        everyText = new JLabel("Show Every Simulation");
        everyText.setFont(myFont);
        everyText.setForeground(Color.green);
        panel.add(everyText);
        panel.add(everyCheckBox);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        soundText = new JLabel("Sound");
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
        panel.getGraphics().fillRect(0, 0, Constants.SCREEN_SIZES.WIDTH, Constants.SCREEN_SIZES.HEIGHT);
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
        boolean customDataSet = customCheckBox.isSelected();
        boolean waitForClick = waitCheckBox.isSelected();
        this.soundOn = soundCheckBox.isSelected();

        // Create a new Graph Screen to be displayed
        graphScreen = new GraphScreen(this, size, waitForClick);

        // Create a new Simulation to be ran
        Sim newSimulation = new Sim(graphScreen, size, algorithm, numSims, customDataSet, showSimulations);
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

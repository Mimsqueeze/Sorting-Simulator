import javax.swing.*;
import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    // All the Java Swing components used for the simulation 
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
    JLabel title;
    JLabel algorithmText;
    JLabel sizeText;
    JLabel simText; 
    JLabel customText;
    JLabel everyText;
    JLabel soundText;
    JLabel waitText;
    // GraphScreen object to visualize the sorting algorithm 
    GraphScreen graphScreen;
    // Boolean for whether or not the sound is turned on 
    boolean soundOn;
    Main() {
        frame = new JFrame("Sorting Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setBackground(Color.black);
        // Starts the simulation at the selection screen 
        start();
    }
    public void start() {
        // Clears the panel and creates the layout/dimensions of the panel 
        panel.removeAll();
        panel.setLayout(new GridLayout(25, 1));
        panel.setPreferredSize(new Dimension(Constants.SCREEN_SIZES.WIDTH, Constants.SCREEN_SIZES.HEIGHT));
        frame.setContentPane(panel);

        // Title 
        title = new JLabel("Sorting Algorithm Simulator");
        title.setFont(titleFont);
        title.setForeground(Color.green);
        panel.add(title);

        // Divider 
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Dropdown menu for sorting algorithm selection 
        algorithmText = new JLabel("Sorting Algorithm");
        algorithmText.setFont(myFont);
        algorithmText.setForeground(Color.green);
        panel.add(algorithmText);
        dropDown = new JComboBox<>(sortingAlgs);
        dropDown.setFont(myFont);
        panel.add(dropDown);

        // Divider
        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Section for input of the size of the array to be sorted
        sizeText = new JLabel("Enter Size");
        sizeText.setFont(myFont);
        sizeText.setForeground(Color.green);
        panel.add(sizeText);
        // Adds the text field for the input
        sizeInput.setFont(myFont);
        panel.add(sizeInput);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Section to input the number of simulations
        simText = new JLabel("Enter Number of Simulations");
        simText.setFont(myFont);
        simText.setForeground(Color.green);
        panel.add(simText);
        simInput.setFont(myFont);
        panel.add(simInput);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Checkbox for whether the user wants to use their own dataset/array
        customText = new JLabel("Use Custom Dataset");
        customText.setFont(myFont);
        customText.setForeground(Color.green);
        panel.add(customText);
        panel.add(customCheckBox);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Option to go step by step and click continue to go to the next step
        waitText = new JLabel("Wait For Click To Continue");
        waitText.setFont(myFont);
        waitText.setForeground(Color.green);
        panel.add(waitText);
        panel.add(waitCheckBox);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Option to show every simulation (important if the user chooses to run the sorting algorithm more than once)
        everyText = new JLabel("Show Every Simulation");
        everyText.setFont(myFont);
        everyText.setForeground(Color.green);
        panel.add(everyText);
        panel.add(everyCheckBox);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Option to add sound
        soundText = new JLabel("Sound");
        soundText.setFont(myFont);
        soundText.setForeground(Color.green);
        panel.add(soundText);
        panel.add(soundCheckBox);

        panel.add(Box.createRigidArea(new Dimension(10, 0)));

        // Start and Exit button
        run = new JButton("Start Simulation");
        run.setFont(myFont);
        panel.add(run);

        exit = new JButton("Exit");
        exit.setFont(myFont);
        panel.add(exit);

        // Adds action listeners to the buttons 
        run.addActionListener(e -> runClick());
        exit.addActionListener(e -> exitClick());
        
        frame.pack();
        frame.setVisible(true);
    }
    private void exitClick() {
        System.exit(0);
    }
    private void runClick() {
        // Removes all components of the panel (buttons, textfields, etc)
        panel.removeAll();

        // Makes the background black
        panel.getGraphics().setColor(Color.BLACK);
        panel.getGraphics().fillRect(0, 0, Constants.SCREEN_SIZES.WIDTH, Constants.SCREEN_SIZES.HEIGHT);
        
        // Adds a mouse listener for the restart and next button
        panel.setFocusable(true);
        panel.requestFocusInWindow();
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point2D click = e.getPoint();
                graphScreen.onClick((int) click.getX(), (int) click.getY());
            }
        });
        
        // Gets all the information inputted by the user
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
